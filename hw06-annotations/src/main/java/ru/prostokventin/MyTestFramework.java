package ru.prostokventin;

import ru.otus.reflection.ReflectionHelper;
import ru.prostokventin.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class MyTestFramework {

    public static void main(String[] args) {
        testProcessor("ru.prostokventin.TestClass");
    }

    private static <T> void testProcessor(String className) {

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        printTestsResult(runTests(clazz));
    }

    private static List<Method> getAnnotatedMethods(Class clazz, Class<? extends Annotation> a) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(a))
                .toList();
    }

    private static TestsResult runTests(Class clazz) {
        List<Method> beforeMethods = getAnnotatedMethods(clazz, Before.class);
        List<Method> afterMethods = getAnnotatedMethods(clazz, After.class);
        List<Method> testMethods = getAnnotatedMethods(clazz, Test.class);

        TestsResult result = new TestsResult(0,0, testMethods.size());
        for (Method test : testMethods) {
            Object instance = ReflectionHelper.instantiate(clazz);
            for (Method before : beforeMethods) {
                ReflectionHelper.callMethod(instance, before.getName());
            }
            try {
                ReflectionHelper.callMethod(instance, test.getName());
                result.addSucceed();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                result.addFailed();
            }
            for (Method after : afterMethods) {
                ReflectionHelper.callMethod(instance, after.getName());
            }
        }

        return result;
    }

    private static void printTestsResult(TestsResult result) {
        System.out.println("Total tests count: " + result.getTotal());
        System.out.println("Succeed tests count: " + result.getSucceed());
        System.out.println("Failed tests count: " + result.getFailed());
    }

}
