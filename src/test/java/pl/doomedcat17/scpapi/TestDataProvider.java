package pl.doomedcat17.scpapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TestDataProvider {

    private static Document sampleScpDocument;

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

    public static Element getSamplePageContent() {
        if (sampleScpDocument == null) {
            try {
                sampleScpDocument = (Document) loadElementFormHTML("src/test/resources/html/scp1.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sampleScpDocument.getElementById("page-content");
    }

    public static Element getSampleElements(String path) {
        return loadElementFormHTML(path)
                .getElementById("elements");

    }

    public static Map<String, ScpObject> getExpectedScpOutputs(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, ScpObject> outputs = null;
        try {
            outputs = objectMapper.readValue(new File(path), new TypeReference<Map<String, ScpObject>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputs;
    }

    public static Map<String, Appendix> getExpectedAppendixOutputs(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Appendix> outputs = null;
        try {
            outputs = objectMapper.readValue(new File(path), new TypeReference<Map<String, Appendix>>() {
            });
            outputs.forEach((key, value) -> {
                value.getContents().forEach(contentBox -> {
                    if (contentBox.getContent() instanceof ArrayList) {
                        ((ArrayList) contentBox.getContent()).forEach(content -> {
                            if (contentBox.getContent() instanceof LinkedHashMap) {
                                ContentBox conerted = objectMapper.convertValue(LinkedHashSet.class, ContentBox.class);
                            }
                        });

                    }
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputs;
    }

    public static ScpObject getSampleScpObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        ScpObject scpObject = null;
        try {
            scpObject = objectMapper
                    .readValue(
                            new File("src/test/resources/html/test_data/sample_scp.json"),
                            new TypeReference<ScpObject>() {
                            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scpObject;
    }

    public static Appendix getSampleAppendix() {
        ObjectMapper objectMapper = new ObjectMapper();
        Appendix appendix = null;
        try {
            appendix = objectMapper
                    .readValue(
                            new File("src/test/resources/html/test_data/sample_appendix.json"),
                            new TypeReference<Appendix>() {
                            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appendix;
    }

    private void convertContentBox(ContentBox<List<LinkedHashMap>> contentBox) {
    }
}

