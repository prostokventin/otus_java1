package ru.prostokventin;

import ru.otus.reflection.ReflectionHelper;
import ru.prostokventin.annotations.After;
import ru.prostokventin.annotations.Before;
import ru.prostokventin.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestProcessor {

    private Class clazz;

    public TestProcessor(String className) {
        try {
            this.clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Method> getAnnotatedMethods(Class clazz, Class<? extends Annotation> a) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(a))
                .toList();
    }

    public TestsResult runTests() {
        List<Method> beforeMethods = getAnnotatedMethods(clazz, Before.class);
        List<Method> afterMethods = getAnnotatedMethods(clazz, After.class);
        List<Method> testMethods = getAnnotatedMethods(clazz, Test.class);

        TestsResult result = new TestsResult(0, 0, testMethods.size());

        for (Method test : testMethods) {
            Object instance = ReflectionHelper.instantiate(clazz);

            try {
                // пытаемся выполнить методы before
                // если не получилось, кидаем RuntimeException
                for (Method before : beforeMethods) {
                    try {
                        before.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Ошибка при выполнение before метода " + before.getName());
                    }
                }

                // пытаемся выполнить метод test
                test.invoke(instance);
                result.addPassed();

            // если упал метод test
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                result.addFailed();

            // если упал метод before
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return result;

            // в любом случае пытаемся выполнить методы after
            } finally {
                for (Method after : afterMethods) {
                    try {
                        after.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

}

