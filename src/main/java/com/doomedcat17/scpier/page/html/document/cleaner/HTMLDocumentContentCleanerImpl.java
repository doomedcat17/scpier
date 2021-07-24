package com.doomedcat17.scpier.page.html.document.cleaner;

import com.doomedcat17.scpier.exception.DocumentContentCleanupException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLDocumentContentCleanerImpl implements DocumentContentCleaner {

    private final String REMOVAL_DEFINITIONS_PATH = "removalElementsDefinitions.json";

    private final List<String> removalDefinitions;

    public void clearContentAndUnpackBlocks(Element content) {
        try {
            removeTrash(content);
            unpackNodes(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DocumentContentCleanupException(e.getMessage());
        }
    }

    public void removeTrash(Element content) {
        removeTrashElements(content);
        removeEmptyNodes(content);
    }

    private void unpackNodes(Element content)  {
        //in some cases content has less than 5 element then it's unpacked
        List<Element> divs = content.children().stream().filter(element ->
                element.is("div, blockquote, section"))
                .filter(element ->
                        element.is(":not(.scp-image-block)")).collect(Collectors.toList());
        if (content.children().size() <= 4 && !divs.isEmpty()) {
            for (Element element: divs) {
                List<Node> nodes = unpackBlock(element);
                int index = element.siblingIndex();
                element.remove();
                content.insertChildren(index, nodes);
            }
            clearContentAndUnpackBlocks(content);
        }

    }


    private List<Node> unpackBlock(Element divElement) {
        if (divElement.is(".collapsible-block")) {
            return divElement
                    .getElementsByClass("collapsible-block-content")
                    .get(0)
                    .childNodesCopy();
        } else if(divElement.hasClass("yui-navset") || divElement.hasClass("yui-navset-top")) {
            Element yuiContent = divElement.getElementsByClass("yui-content").first();
            List<Node> nodes = new ArrayList<>();
            yuiContent.children().forEach(div -> nodes.addAll(div.childNodesCopy()));
            return nodes;
        } else return divElement.childNodesCopy();
    }


    private void removeEmptyNodes(Element content) {
        //content childNodes list could not be edited due to ConcurrentModificationException
        //elementChildren is copy of content childNodes and then content is cleared
        //
        ArrayList<Node> elementChildren = new ArrayList<>(content.childNodesCopy());
        content.empty();
        //insert of filtered childNodes
        content.insertChildren(0, filterChildren(elementChildren));
    }

    private void removeTrashElements(Element content) {
        for (String name: removalDefinitions) {
            Elements elements = content.select(name);
            if (!elements.isEmpty()) {
                elements.forEach(Node::remove);
            }
        }
        //deleting <br> element only from <div id="content-content">
        Elements elements = content.children();
        for (Element element: elements) {
            if (element.is("br")) element.remove();
        }
    }

    //deleting empty nodes from content
    private List<Node> filterChildren(List<Node> nodes) {
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if ((!element.is("img, audio, iframe, audio-player, video") && element.select("img, audio, iframe, audio-player, video").isEmpty()) && (element.childNodes().isEmpty() || !element.hasText())) {
                    nodesToRemove.add(node);
                }
            } else {
                if (node.childNodes().isEmpty() && node.toString().isBlank()) {
                    nodesToRemove.add(node);
                }
            }
        }
        nodes.removeAll(nodesToRemove);
        return nodes;
    }

    public HTMLDocumentContentCleanerImpl() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(REMOVAL_DEFINITIONS_PATH);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = streamReader.readLine()) != null) jsonBuilder.append(line);
            removalDefinitions =
                    objectMapper.readValue(
                            jsonBuilder.toString(),
                            new TypeReference<>() {
                            });
        } catch (IOException e) {
            throw new RuntimeException("Could not find Removal Definitions!");
        }
    }
}
