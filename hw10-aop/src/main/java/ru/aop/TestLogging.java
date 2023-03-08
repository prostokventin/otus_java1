package ru.aop;

import ru.aop.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("result = " + param * 10);
    }

    @Override
    public void calculation(int param1, int param2) {
        int result = param1 + param2;
        System.out.println("result = " + result);
    }

}
