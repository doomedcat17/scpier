package com.doomedcat17.scpier.data.files.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class RemovalDefinitionsLoader {

    public Set<String> loadRemovalDefinitions() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("removalElementsDefinitions.json");
        byte[] file = inputStream.readAllBytes();
        return objectMapper.readValue(
                file,
                new TypeReference<>() {
                });
    }
}
