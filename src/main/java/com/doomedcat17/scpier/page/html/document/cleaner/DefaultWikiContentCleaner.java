package com.doomedcat17.scpier.page.html.document.cleaner;

import com.doomedcat17.scpier.exception.page.html.document.cleaner.WikiContentCleanerException;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultWikiContentCleaner implements WikiContentCleaner {

    private final Set<String> removalDefinitions;

    public void clearContentAndUnpackBlocks(Element content) {
        try {
            removeTrash(content);
            unpackNodes(content);
        } catch (Exception e) {
            throw new WikiContentCleanerException(e);
        }
    }

    public void removeTrash(Element content) {
        removeTrashElements(content);
        removeEmptyNodes(content);
    }

    @Override
    public void additionalRemovalDefinitions(List<String> definitions) {
        removalDefinitions.addAll(definitions);
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
            Element blockContent = divElement.selectFirst(".collapsible-block-content");
            if (blockContent.children().isEmpty()) {
                blockContent.siblingElements().forEach(sibling -> {
                    if (sibling.is("div")) {
                        List<Node> nodes = unpackBlock(sibling);
                        nodes.forEach(blockContent::appendChild);
                    } else blockContent.appendChild(sibling);
                });
            }
            return blockContent.childNodesCopy();
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
