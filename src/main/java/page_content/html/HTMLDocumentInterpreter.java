package page_content.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import page_content.PageContent;
import page_content.html.redirection.RedirectionContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HTMLDocumentInterpreter {

    private final String[] elementsToRemove = {".footer-wikiwalk-nav", ".page-rate-widget-box", ".licensebox22",".licensebox", ".creditRate", "#u-credit-view", ".info-container", ".meta-title", ".error-block", "hr", "script"};

    public void mapDocument(Document contentDocument, PageContent pageContent) throws IOException {
        Element content = contentDocument.getElementById("page-content");
        pageContent.setScpName(contentDocument.getElementById("page-title").text().trim());
        clearContent(content);
        pageContent.setContent(content);
    }

    private void clearContent(Element content) throws IOException {
        removeTrashElements(content);
        removeEmptyNodes(content);
        unpackNodes(content);

    }

    private void unpackNodes(Element content) throws IOException {
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
        for (String name: elementsToRemove) {
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
