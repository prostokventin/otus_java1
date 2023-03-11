package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String filename;

    public FileSerializer(String fileName) {
        this.filename = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (FileWriter file = new FileWriter(filename)) {
            var gson = new Gson();
            SortedMap<String, Double> sordedData = new TreeMap(data);
            gson.toJson(sordedData, file);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
