package ru.otus.appcontainer;

import ru.otus.App;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.io.ObjectStreamException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        Object instance = null;
        try {
            instance = configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .toList();

        if (methods.stream().map(m -> m.getAnnotation(AppComponent.class).name()).distinct().count() != methods.size()){
            throw new RuntimeException("components have duplicates");
        }

        for (var method : methods) {
            var componentName = method.getAnnotation(AppComponent.class).name();

            var paramTypes = Arrays.stream(method.getParameterTypes()).toList();
            if (paramTypes.size() == 0) {
                try {
                    var component = method.invoke(instance);
                    appComponents.add(component);
                    appComponentsByName.put(componentName, component);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                List<Object> args = new ArrayList<>();
                for (var type : paramTypes) {
                    for (var component : appComponents) {
                        if (type.isAssignableFrom(component.getClass())) {
                            args.add(component);
                        }
                    }
                }

                try {
                    var component = method.invoke(instance, args.toArray());
                    appComponents.add(component);
                    appComponentsByName.put(componentName, component);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }

    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> result = appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .toList();

        if (result.size() == 0) throw new RuntimeException(componentClass.getSimpleName() + " has not found");
        if (result.size() > 1) throw new RuntimeException("Found more than one component");

        return (C) result.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var appComponent = (C) appComponentsByName.get(componentName);
        if (appComponent == null) {
            throw new RuntimeException(componentName + " has not found");
        }
        return appComponent;
    }
}
