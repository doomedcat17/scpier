package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class TitleResolverProvider {

    public static TitleResolver getTitleResolver(String languageIdentifier) {
        ObjectMapper objectMapper = new ObjectMapper();
        String itemNamePatternsPath = "src/main/resources/patterns/"+languageIdentifier+"/item_name_patterns.json";
        String titlePatternsPath = "src/main/resources/patterns/"+languageIdentifier+"/title_patterns.json";
        String titleIgnoredPath = "src/main/resources/patterns/"+languageIdentifier+"/title_ignored.json";
        List<String> itemNamePatterns = null, titlePatterns = null, titleIgnored = null;
        try {
            itemNamePatterns = objectMapper.readValue(new File(itemNamePatternsPath),
                    new TypeReference<List<String>>(){});
            titlePatterns = objectMapper.readValue(new File(titlePatternsPath),
                    new TypeReference<List<String>>(){});
            titleIgnored = objectMapper.readValue(new File(titleIgnoredPath),
                    new TypeReference<List<String>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultTitleResolver(titlePatterns, itemNamePatterns, titleIgnored);
    }
}
