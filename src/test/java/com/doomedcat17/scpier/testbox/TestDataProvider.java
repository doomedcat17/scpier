package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    private static Document sampleScpDocument;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static Map<String, List<ContentNode<?>>> getSampleContentNodes(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            HashMap<String, List<ContentNode<?>>> outputs =
                    objectMapper.readValue(new File(path), new TypeReference<HashMap<String, List<ContentNode<?>>>>(){});
            return outputs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Element loadElementFromHTML(String path) {
        Element element = null;
        try {
            element = Jsoup.parse(
                    new File(path),
                    "UTF-8",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    public static Document loadDocumentFormHTML(String path) {
        Document document = null;
        try {
            document = Jsoup.parse(
                    new File(path),
                    "UTF-8",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Preset loadPresetFromYAML(String path) {
       ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID));
       try {
           return objectMapper.readValue(new File(path), new TypeReference<Preset>(){});
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    public static WikiContent getPageContent(String path) {
        Document scpDocument = loadDocumentFormHTML(path);
        WikiContent wikiContent = new WikiContent();
        wikiContent.setContent(scpDocument.getElementsByTag("body").first());
        wikiContent.setOriginalSourceUrl("url");
        wikiContent.setLastRevisionTimestamp(LocalDateTime.now());
        return wikiContent;
    }

    public static Element getSampleElement(String path) {
        return loadElementFromHTML(path)
                .getElementById("page-content");

    }
}

