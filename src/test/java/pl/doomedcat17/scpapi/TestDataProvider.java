package pl.doomedcat17.scpapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
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
                .getElementById("page-content");

    }

    public static Map<String, List<Appendix>> getExpectedAppendicesOutputs(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Appendix>> outputs = null;
        try {
            outputs = objectMapper.readValue(new File(path), new TypeReference<Map<String, List<Appendix>>>() {
            });
            for (Map.Entry<String, List<Appendix>> entry : outputs.entrySet()) {
                for (Appendix appendix : entry.getValue()) {
                    List<ContentNode<?>> contentNodes = appendix.getContents();
                    for (int i = 0; i < contentNodes.size(); i++) {
                        if (contentNodes.get(i).getContent() instanceof List) {
                            contentNodes.set(i, mapContentNode(contentNodes.get(i)));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputs;
    }

    private static ContentNode<List<ContentNode<?>>> mapContentNode(ContentNode<?> contentNode) {
        List<ContentNode<?>> mappedContentNodes = applyList((ArrayList<LinkedHashMap>) contentNode.getContent());
        for (int i = 0; i < mappedContentNodes.size(); i++) {
            if (mappedContentNodes.get(i).getContent() instanceof List) {
                mappedContentNodes.set(i, mapContentNode(mappedContentNodes.get(i)));
            }
        }
        ContentNode<List<ContentNode<?>>> listContentNode = new ContentNode<>();
        listContentNode.setContentNodeType(contentNode.getContentNodeType());
        listContentNode.setContent(mappedContentNodes);
        return listContentNode;
    }

    private static List<ContentNode<?>> applyList(ArrayList<LinkedHashMap> contentNodes) {
        List<ContentNode<?>> mappedContentNodes = new ArrayList<>();
        contentNodes.forEach(node -> mappedContentNodes.add(applyHashMap(node)));
        return mappedContentNodes;
    }


    private static ContentNode<?> applyHashMap(LinkedHashMap<String, String> linkedHashMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(linkedHashMap, new TypeReference<ContentNode<?>>() {
        });
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
}

