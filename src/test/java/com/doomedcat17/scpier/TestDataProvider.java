package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
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

    private static Element loadElementFormHTML(String path) {
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

    public static PageContent getPageContent(String path) {
        Document scpDocument = loadDocumentFormHTML(path);
        PageContent pageContent = new PageContent();
        pageContent.setContent(scpDocument.getElementsByTag("body").first());
        pageContent.setSourceUrl("url");
        return pageContent;
    }

    public static Element getSampleElements(String path) {
        return loadElementFormHTML(path)
                .getElementById("page-content");

    }
}

