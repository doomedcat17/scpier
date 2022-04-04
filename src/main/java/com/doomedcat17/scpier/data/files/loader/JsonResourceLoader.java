package com.doomedcat17.scpier.data.files.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JsonResourceLoader {

    public Set<String> loadSet(String resourcePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        byte[] file = inputStream.readAllBytes();
        inputStream.close();
        return objectMapper.readValue(
                file,
                new TypeReference<>() {
                });
    }
}
