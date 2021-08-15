package com.doomedcat17.scpier.data.files.loader;

import com.doomedcat17.scpier.page.html.document.provider.offset.OffsetPattern;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class OffsetPatternsLoader {


    public Set<OffsetPattern> loadOffsetPatterns() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("offsetPatterns.json");
        byte[] file = inputStream.readAllBytes();
        inputStream.close();
        return objectMapper.readValue(
                file,
                new TypeReference<>() {
                });
    }
}
