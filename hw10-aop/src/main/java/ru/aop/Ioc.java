package ru.aop;

import ru.aop.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new Handler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class Handler implements InvocationHandler {
        private final TestLoggingInterface myclass;

        public Handler(TestLoggingInterface myclass) {
            this.myclass = myclass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method classMethod = myclass.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (classMethod.isAnnotationPresent(Log.class)) {
                var params = method.getParameters();
                StringBuilder paramsOutput = new StringBuilder("");
                classMethod.getParameterCount();
                for (int i = 0; i < method.getParameterCount(); i++) {
                    paramsOutput.append(String.format("%s: %s ", params[i].getName(), args[i].toString()));
                }
                System.out.println("executed method: " + method.getName() + ", " + paramsOutput);
            }
            return method.invoke(myclass, args);
        }
    }
}
