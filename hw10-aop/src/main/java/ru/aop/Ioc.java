package ru.aop;

import ru.aop.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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
        private List<Method> loggedClassMethods;

        public Handler(TestLoggingInterface myclass) {
            this.myclass = myclass;
            this.loggedClassMethods = Arrays.stream(myclass.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .toList();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (checkMethodHasLogAnnotation(method)) {
                printMethodMetaInfo(method, args);
            }
            return method.invoke(myclass, args);
        }

        private void printMethodMetaInfo(Method method, Object[] args) {
            Parameter[] methodParams = method.getParameters();
            StringJoiner methodMetaInfo = new StringJoiner(",", "executed method: " + method.getName() + ", ", "");
            for (int i = 0; i < args.length; i++) {
                methodMetaInfo.add(String.format("%s: %s ", methodParams[i].getName(), args[i].toString()));
            }
            System.out.println(methodMetaInfo);
        }

        private boolean checkMethodHasLogAnnotation(Method method) {
            for (var classMethod : loggedClassMethods) {
                if (method.getName().equals(classMethod.getName()) && Arrays.equals(method.getParameterTypes(), classMethod.getParameterTypes())) {
                    return true;
                }
            }
            return false;
        }
    }
}
