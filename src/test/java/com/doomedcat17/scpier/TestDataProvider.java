package com.doomedcat17.scpier;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.time.LocalDateTime;

public class TestDataProvider {

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
           return objectMapper.readValue(new File(path), new TypeReference<>(){});
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
        wikiContent.setLastRevisionDate(LocalDateTime.now());
        return wikiContent;
    }

    public static Element getSampleElement(String path) {
        return loadElementFromHTML(path)
                .getElementById("page-content");

    }
}

