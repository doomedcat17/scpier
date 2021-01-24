package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLDocumentInterpreter {

    private final String[] elementsToRemove = {".footer-wikiwalk-nav", ".page-rate-widget-box", ".licensebox22",".licensebox", ".creditRate", "#u-credit-view", ".info-container", "br", "hr", ".meta-title"};


    public Element parseDocument(Document pageContent) throws IOException {
        Element content = pageContent.getElementById("page-content");
        clearContent(content);
        return content;
    }

    private void clearContent(Element content) throws IOException {
        removeTrashElements(content);
        removeEmptyNodes(content);
        unpackNodesFromDivs(content);

    }

    private void unpackNodesFromDivs(Element content) throws IOException {
        //in some cases content has less than 5 elements then it's unpacked
        if (content.children().size() < 4 && !content.children().select("div").isEmpty()) {
            ArrayList<Node> nodes = new ArrayList<>();
            for (Node node: content.childNodesCopy()) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.is("div")) {
                        nodes.addAll(unpackDiv(element));
                    } else nodes.add(node);
                } else nodes.add(node);
            }
            content.empty();
            content.insertChildren(0, nodes);
            clearContent(content);
        }

    }


    private List<Node> unpackDiv(Element divElement) throws IOException {
        if (divElement.is("#u-adult-warning")) {
            RedirectionContent redirectionContent = new RedirectionContent();
            return redirectionContent.getRedirectionContent("u-adult-warning", divElement);
        } else if (divElement.is(".collapsible-block")) {
            return divElement
                    .getElementsByClass("collapsible-block-content")
                    .get(0)
                    .childNodes();
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
        for (String name: elementsToRemove) {
            Elements elements = content.select(name);
            if (!elements.isEmpty()) {
                elements.forEach(Node::remove);
            }
        }
    }

    //deleting empty nodes from content
    private List<Node> filterChildren(List<Node> nodes) {
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (!element.is("img") && (element.childNodes().isEmpty() || !element.hasText())) {
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
}
