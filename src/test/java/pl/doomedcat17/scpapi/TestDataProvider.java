package pl.doomedcat17.scpapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.Image;

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
        /*
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
                        } else if (contentNodes.get(i).getContent() instanceof LinkedHashMap && contentNodes.get(i).getContentNodeType().equals(ContentNodeType.IMAGE)) {
                            contentNodes.set(i,mapImageContentNode((LinkedHashMap<String, String>) contentNodes.get(i).getContent()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputs;

         */
        ObjectMapper objectMapper = new ObjectMapper();

        return null;
    }

    private Appendix mapJsonNodeToAppendix(JsonNode jsonNode) {
        Appendix appendix = new Appendix();
        appendix.setTitle(jsonNode.get("title").asText());
        appendix.setTitle(jsonNode.get("title").asText());
        return null;
    }

    private static ContentNode<Image> mapImageContentNode(LinkedHashMap<String, String> linkedHashMap) {
        return new ContentNode<>(ContentNodeType.IMAGE,
                new Image(linkedHashMap.get("source"),
                        linkedHashMap.get("caption")));
    }

    private static ContentNode<List<ContentNode<?>>> mapContentNode(ContentNode<?> contentNode) {
        List<ContentNode<?>> mappedContentNodes = applyList((ArrayList<LinkedHashMap>) contentNode.getContent());
        for (int i = 0; i < mappedContentNodes.size(); i++) {
            if (mappedContentNodes.get(i).getContent() instanceof List) {
                mappedContentNodes.set(i, mapContentNode(mappedContentNodes.get(i)));
            } else if (mappedContentNodes.get(i).getContent() instanceof LinkedHashMap && mappedContentNodes.get(i).getContentNodeType().equals(ContentNodeType.IMAGE)) {
                mappedContentNodes.set(i,mapImageContentNode((LinkedHashMap<String, String>) mappedContentNodes.get(i).getContent()));
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

