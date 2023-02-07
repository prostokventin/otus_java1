package ru.prostokventin;

import java.lang.reflect.InvocationTargetException;

public class MyTestFramework {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestProcessor testProcessor = new TestProcessor("ru.prostokventin.TestClass");
        TestsResult result = testProcessor.runTests();
        System.out.println(result.toString());
    }

}
