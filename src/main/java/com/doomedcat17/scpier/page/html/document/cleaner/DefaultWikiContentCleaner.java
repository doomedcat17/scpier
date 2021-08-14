package com.doomedcat17.scpier.page.html.document.cleaner;

import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultWikiContentCleaner implements WikiContentCleaner {

    private final Set<String> removalDefinitions;

    public void removeTrashNodes(Element content) {
        removeTrashElements(content);
        removeEmptyNodes(content);
    }

    @Override
    public void additionalRemovalDefinitions(List<String> definitions) {
        removalDefinitions.addAll(definitions);
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
        Set<Element> elementsToRemove = new HashSet<>();
        for (String name: removalDefinitions) {
            elementsToRemove.addAll(content.select(name));
        }
        //deleting <br> element only from <div id="page-content">
        Elements brElements = content.select("#page-content > br");
        elementsToRemove.addAll(brElements);
        for(Element element: elementsToRemove) {
            element.remove();
        }
    }

    //deleting empty nodes from content
    private List<Node> filterChildren(List<Node> nodes) {
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if ((!element.is("img, audio, iframe, audio-player, video")
                        && element.select("img, audio, iframe, audio-player, video").isEmpty())
                        && (element.childNodes().isEmpty() || !element.hasText())) {
                    nodesToRemove.add(node);
                }
            } else {
                if ((node.childNodes().isEmpty() && node.toString().isBlank()) || node instanceof Comment) {
                    nodesToRemove.add(node);
                }
            }
        }
        nodes.removeAll(nodesToRemove);
        return nodes;
    }

    public DefaultWikiContentCleaner(Set<String> removalDefinitions) {
        this.removalDefinitions = removalDefinitions;
    }

}
