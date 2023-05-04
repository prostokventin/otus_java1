package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;

    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    List<Field> fields = entityClassMetaData.getAllFields();
                    List<Object> queryValues = new ArrayList<>();
                    for (var field : fields) {
                        queryValues.add(rs.getObject(field.getName()));
                    }
                    return (T) entityClassMetaData.getConstructor().newInstance(queryValues.toArray());
                }
                return null;
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            List<T> resultList = new ArrayList<>();
            try {
                while (rs.next()) {
                    List<Field> fields = entityClassMetaData.getAllFields();
                    List<Object> queryValues = new ArrayList<>();
                    for (var field : fields) {
                        queryValues.add(rs.getObject(field.getName()));
                    }
                    resultList.add((T) entityClassMetaData.getConstructor().newInstance(queryValues.toArray()));
                }
                return resultList;
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> queryParams = new ArrayList<>();
//            for (var method : entityClassMetaData.getGettersWithoutIdFieldGetter()) {
//                Object returnedObj = method.invoke(client);
//                queryParams.add(returnedObj);
//            }
            for (var field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                queryParams.add(field.get(client));
            }
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), queryParams);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> queryParams = new ArrayList<>();

            for (var field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                queryParams.add(field.get(client));
            }

            Field id = entityClassMetaData.getIdField();
            id.setAccessible(true);
            queryParams.add(id.get(client));

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), queryParams);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
