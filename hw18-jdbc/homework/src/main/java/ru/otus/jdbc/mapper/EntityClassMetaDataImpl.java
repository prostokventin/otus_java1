package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private final Class clazz;
    private final String name;
    private final Field[] fields;
    private final Method[] methods;

    public EntityClassMetaDataImpl(Class clazz) {
        try {
            this.clazz = clazz;
            this.name = clazz.getSimpleName();
            this.fields = clazz.getDeclaredFields();
            this.methods = clazz.getDeclaredMethods();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor getConstructor() throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(Arrays.stream(fields).map(Field::getType).toArray(Class<?>[]::new));
    }

    @Override
    public Field getIdField() {
        for (var field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(fields)
                .toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(fields)
                .filter(field -> !field.equals(getIdField()))
                .toList();
    }
}
