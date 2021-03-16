package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolverProvider;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrapperTest {

    protected TitleResolver titleResolver = TitleResolverProvider.getTitleResolver("eng");

    protected final String SOURCE = "http://www.scpwiki.com/scp-test";

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected Map<String, List<Appendix>> getExpectedAppendicesOutputs(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            Map<String, List<Appendix>> outputs =
                    objectMapper.readValue(new File(path), new TypeReference<HashMap<String, List<Appendix>>>(){});
            return outputs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
