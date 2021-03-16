package com.doomedcat17.scpier.page_content.html.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentContentCleanerImpl implements DocumentContentCleaner {

    private final String REMOVAL_DEFINITIONS_PATH = "src/main/resources/removalElementsDefinitions.json";

    private final List<String> removalDefinitions;

    public void clearContentAndUnpackBlocks(Element content) {
        removeTrash(content);
        unpackNodes(content);
    }

    public void removeTrash(Element content) {
        removeTrashElements(content);
        removeEmptyNodes(content);
    }

    private void unpackNodes(Element content)  {
        //in some cases content has less than 5 elements then it's unpacked
        if (content.children().size() < 5 && (!content.children().select("div").isEmpty() || !content.children().select("blockquote").isEmpty())) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (Node node: content.childNodesCopy()) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.is("div") && !element.hasClass("scp-image-block")) {
                        nodes.addAll(unpackDiv(element));
                    } else if (element.is("blockquote")) {
                        nodes.addAll(element.childNodes());
                    } else nodes.add(node);
                } else nodes.add(node);
            }
            content.empty();
            content.insertChildren(0, nodes);
            clearContentAndUnpackBlocks(content);
        }

    }


    private List<Node> unpackDiv(Element divElement) {
        if (divElement.is(".collapsible-block")) {
            return divElement
                    .getElementsByClass("collapsible-block-content")
                    .get(0)
                    .childNodes();
        } else if(divElement.hasClass("yui-navset") || divElement.hasClass("yui-navset-top")) {
            Element yuiContent = divElement.getElementsByClass("yui-content").first();
            List<Node> nodes = new ArrayList<>();
            yuiContent.children().forEach(div -> nodes.addAll(div.childNodes()));
            return nodes;
        } else return divElement.childNodes();
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
        //deleting <br> elements only from <div id="page-content">
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

    public DocumentContentCleanerImpl() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            removalDefinitions =
                    objectMapper.readValue(
                            new File(REMOVAL_DEFINITIONS_PATH),
                            new TypeReference<>() {
                            });
        } catch (IOException e) {
            throw new RuntimeException("Could not find Removal Definitions!");
        }
    }
}
