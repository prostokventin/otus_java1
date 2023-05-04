package ru.otus.jdbc.mapper;

import org.flywaydb.core.internal.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {


        String tableName = entityClassMetaData.getName();


        String allFields = String.join(", ",
                entityClassMetaData.getAllFields().stream()
                        .map(Field::getName)
                        .toList()
        );

        return String.format("select %s from %s", allFields, tableName);
    }

    @Override
    public String getSelectByIdSql() {
        String tableName = entityClassMetaData.getName();
        String id = entityClassMetaData.getIdField().getName();
        String allFields = String.join(", ",
                entityClassMetaData.getAllFields().stream()
                        .map(Field::getName)
                        .toList()
        );
        return String.format("select %s from %s where %s = ?", allFields, tableName, id);
    }

    @Override
    public String getInsertSql() {
        String tableName = entityClassMetaData.getName();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        String fieldsWithoutIdStr = String.join(", ",
                fieldsWithoutId.stream()
                        .map(Field::getName)
                        .toList());

        String valuesQuestionMarks = "?" + ", ?".repeat(fieldsWithoutId.size() - 1);
        return String.format("insert into %s(%s) values (%s)", tableName, fieldsWithoutIdStr, valuesQuestionMarks);
    }

    @Override
    public String getUpdateSql() {
        String tableName = entityClassMetaData.getName();
        String updatedFields = String.join(" = ?, ",
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .toList()
        );
        String id = entityClassMetaData.getIdField().getName();
        return String.format("update %s set %s = ? where %s = ?", tableName, updatedFields, id);
    }

}

