package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrapperTest {

    protected final String SOURCE = "http://www.scpwiki.com/scp-test";

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected Map<String, ContentNode<?>> getExpectedContentNodeOutputs(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            return objectMapper.readValue(new File(path), new TypeReference<HashMap<String, ContentNode<?>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Map<String, List<TextNode>> getListOfExpectedTextNodes(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            return objectMapper.readValue(new File(path), new TypeReference<HashMap<String, List<TextNode>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
