package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
//Надо реализовать эти методы
    Map<K, V> cache = new WeakHashMap<>();
    List<HwListener<K, V>> listenerList = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listenerList.stream().forEach(listener -> listener.notify(key, value, "put"));
        cache.put(key, value);
    }

    @Override
    public void remove(K key) {
        listenerList.stream().forEach(listener -> listener.notify(key, cache.get(key), "remove"));
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        listenerList.stream().forEach(listener -> listener.notify(key, cache.get(key), "get"));
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listenerList.remove(listener);
    }
}
