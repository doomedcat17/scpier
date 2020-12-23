package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class TextScrapper {

    public List<ContentNode<String>> scrapText(Element textElement) {
        List<ContentNode<String>> contentNodes = new ArrayList<>();
        if (textElement.is("[style*=\"text-decoration: line-through;\"]")) {
            contentNodes.add(new ContentNode<>(ContentNodeType.TEXT_DELETED, textElement.text()));
        } else if (textElement.is("sup.footnoteref") || textElement.is("sub.footnoteref")) {
            contentNodes.add(new ContentNode<>(ContentNodeType.TEXT, "[" + textElement.text().trim() + "]"));
        } else {
            ContentNode<String> contentNode = new ContentNode<>(ContentNodeType.TEXT);
            for (Node node : textElement.childNodes()) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.tagName().equals("br")) {
                        appendTextToContentNode(contentNode, "\n");
                    } else if (element.is("[style*=\"text-decoration: line-through;\"]")) {
                        if (contentNode.getContent() != null) contentNodes.add(contentNode);
                        ContentNode<String> deletedText = new ContentNode<>(ContentNodeType.TEXT_DELETED, element.text());
                        contentNodes.add(deletedText);
                        contentNode = new ContentNode<>(ContentNodeType.TEXT);
                    } else if (element.is("sup.footnoteref") || element.is("sub.footnoteref")) {
                        appendTextToContentNode(contentNode, "[" + element.text() + "]");
                    } else {
                        appendTextToContentNode(contentNode, element.text());
                    }
                } else {
                    String text = node.toString();
                    if (!text.isBlank()) {
                        appendTextToContentNode(contentNode, text);
                    }
                }
            }
            if (contentNode.getContent() != null) contentNodes.add(contentNode);
            trimContent(contentNodes);
        }
        return contentNodes;
    }

    private void trimContent(List<ContentNode<String>> contentNodes) {
        //TODO bug z SCP-083
        ContentNode<String> leadingNode = contentNodes.get(0);
        leadingNode.setContent(leadingNode.getContent().stripLeading());
        ContentNode<String> tailingNode = contentNodes.get(contentNodes.size()-1);
        tailingNode.setContent(tailingNode.getContent().stripTrailing());
    }

    private void appendTextToContentNode(ContentNode<String> contentNode, String text) {
        if (contentNode.getContent() == null) {
            contentNode.setContent(text);
        } else contentNode.setContent(contentNode.getContent() + text);
    }
}
