package ru.prostokventin;

import ru.prostokventin.annotations.*;

public class TestClass {

    @Before
    public void beforeMethod1() {
        System.out.println("run before method1");
    }

    @Before
    public void beforeMethod2() {
        System.out.println("run before method2");
//        throw new RuntimeException("Ups");
    }

    @Test
    public void addition() {
        System.out.println("run test 1");
        int a = 10;
        int b = 20;
        int result = a + b;
        System.out.println("Result: " + result);
    }

    @Test
    public void division() {
        System.out.println("run test 2");
        int a = 100;
        int b = 0;
        int result = a / b;
        System.out.println("Result: " + result);
    }

    @After
    public void afterMethod() {
        System.out.println("run after method");
//        throw new RuntimeException("Ups");
    }


}
