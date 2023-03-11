package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (var resource = new InputStreamReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(filename))) {
            var gson = new Gson();
            var result = (List<Measurement>) gson.fromJson(resource, new TypeToken<List<Measurement>>(){}.getType());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
