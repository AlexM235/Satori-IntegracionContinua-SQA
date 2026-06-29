package com.tuproyecto.backend.repositories;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonRepository<T> {
    private final ObjectMapper objectMapper;
    private final String jsonFilePath;
    private final Class<T> type;

    public JsonRepository(String jsonFilePath, Class<T> type) {
        this.jsonFilePath = "src/main/resources/archivosJSON/" + jsonFilePath;
        this.type = type;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .enable(SerializationFeature.INDENT_OUTPUT);
        ensureFileExists();
    }

    private void ensureFileExists() {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                objectMapper.writeValue(file, new ArrayList<>());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar archivo JSON", e);
        }
    }

    protected List<T> loadData() {
        try {
            return objectMapper.readValue(new File(jsonFilePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar datos", e);
        }
    }

    protected void saveData(List<T> data) {
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar datos", e);
        }
    }
}