package com.doomedcat17.scpier.scraper.list;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.List;

public class ListScraper extends ElementScraper {
    public ListScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            ListNode<ListNode<ContentNode<?>>> listNode;
            if (element.tagName().equals("ul")) {
                listNode = new ListNode<>(ContentNodeType.LIST_UL);
            } else if (element.tagName().equals("ol")) {
                listNode = new ListNode<>(ContentNodeType.LIST_OL);
            } else listNode = new ListNode<>(ContentNodeType.LIST_DL);
            scrapList(element, listNode);
            return listNode;
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }

    private void scrapList(Element element, ListNode<ListNode<ContentNode<?>>> listNode) {
        List<Node> children = element.childNodes();
        for (Node child : children) {
            scrapRow(child, listNode);
        }
        listCleanup(listNode);
    }

    private void scrapRow(Node row, ListNode<ListNode<ContentNode<?>>> listNode) {
        if (row instanceof Element) {
            scrapListChildElement((Element) row, listNode);
        } else {
            scrapListChildNode(row, listNode);
        }
    }

    private void scrapListChildElement(Element element, ListNode<ListNode<ContentNode<?>>> listNode) {
        ListNode<ContentNode<?>> listItem;
        if (element.is("br") && !listNode.isEmpty()) {
            listItem = listNode.getLastElement();
            listItem.addElement(new ParagraphNode());
        }
        if (element.childNodeSize() != 0 && !element.wholeText().isBlank()) {
            if (!listNode.isEmpty() && !element.is("li, dt")) listItem = listNode.getLastElement();
            else {
                listItem = new ListNode<>(ContentNodeType.LIST_ITEM);
                listNode.addElement(listItem);
            }
            if (element.is("ul, ol, dl")) {
                listItem.addElement(scrapElement(element));
            } else {
                List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(element, source);
                if (contentNodes.size() == 1) {
                    ContentNode<?> node = contentNodes.get(0);
                    if (node.getContentNodeType().equals(ContentNodeType.CONTENT_NODES)
                            || node.getContentNodeType().equals(ContentNodeType.PARAGRAPHS)) {
                        ListNode<?> content = (ListNode<?>) node;
                        listItem.addElements((List<ContentNode<?>>) content.getContent());
                    } else listItem.addElement(contentNodes.get(0));
                } else {
                    listItem.addElements(contentNodes);
                }
            }
        }
    }

    private void scrapListChildNode(Node node, ListNode<ListNode<ContentNode<?>>> listNode) {
        if (!node.toString().isBlank()) {
            ListNode<ContentNode<?>> listItem;
            TextNode textNode = new TextNode(node.toString());
            if (!listNode.isEmpty()) listItem = listNode.getLastElement();
            else {
                listItem = new ListNode<>(ContentNodeType.LIST_ITEM);
                listNode.addElement(listItem);
            }
            if (!listItem.isEmpty()) {
                if (listItem.getLastElement() instanceof ParagraphNode) {
                    ParagraphNode lastParagraph = (ParagraphNode) listItem.getLastElement();
                    lastParagraph.addElement(textNode);
                }
            } else {
                ParagraphNode paragraphNode = new ParagraphNode();
                paragraphNode.addElement(textNode);
                listItem.addElement(paragraphNode);
            }
        }
    }

    private void listCleanup(ListNode<ListNode<ContentNode<?>>> listNode) {
        for (ListNode<ContentNode<?>> row : listNode.getContent()) {
            if (row.isEmpty()) listNode.getContent().remove(row);
            else {
                row.getContent().removeIf(ContentNode::isEmpty);
            }
        }
    }


}
