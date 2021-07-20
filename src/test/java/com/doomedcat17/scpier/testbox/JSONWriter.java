package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.js.preset.InputWikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.ScpInputPreset;
import com.doomedcat17.scpier.page.html.document.js.preset.WikiElement;
import com.doomedcat17.scpier.page.html.document.js.preset.WikiElementType;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JSONWriter {

    public static void main(String[] args) throws JsonProcessingException {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // ScpWikiData scp = scpFoundationDataProvider.getScpWikiData("3211", SCPBranch.ENGLISH, SCPTranslation.ORIGINAL);
            WikiElement wikiElement = new WikiElement("button", WikiElementType.BUTTON);
            WikiElement inputWikiElement = new InputWikiElement("button", WikiElementType.INPUT, "input text");
            ScpInputPreset scpInputPreset = new ScpInputPreset("173", SCPBranch.ENGLISH, List.of(inputWikiElement, wikiElement));
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(scpInputPreset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String asJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }


}
