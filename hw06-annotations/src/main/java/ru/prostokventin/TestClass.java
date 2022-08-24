package ru.prostokventin;

import ru.prostokventin.annotations.*;

public class TestClass {

    @Before
    public void beforeMethod() {
        System.out.println("run before method");
    }

    @Test
    public void addition() {
        int a = 10;
        int b = 20;
        int result = a + b;
        System.out.println(result);
    }

    @Test
    public void division() {
        int a = 100;
        int b = 0;
        int result = a / b;
        System.out.println(result);
    }

    @After
    public void afterMethod() {
        System.out.println("run after method");
    }


}
