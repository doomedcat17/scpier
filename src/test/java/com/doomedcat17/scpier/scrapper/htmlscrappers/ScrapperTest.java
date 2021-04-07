package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScrapperTest {

    protected final String SOURCE = "http://www.scpwiki.com/scp-test";

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected Map<String, ContentNode<?>> getExpectedAppendicesOutputs(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            Map<String, ContentNode<?>> outputs =
                    objectMapper.readValue(new File(path), new TypeReference<HashMap<String, ContentNode<?>>>(){});
            return outputs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
