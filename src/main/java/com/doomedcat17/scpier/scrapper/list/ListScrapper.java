package com.doomedcat17.scpier.scrapper.list;

import com.doomedcat17.scpier.data.contentnode.*;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class ListScrapper extends ElementScrapper {
    public ListScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            ListNode<ContentNode<?>> listNode;
            if (element.tagName().equals("ul")) {
                listNode = new ListNode<>(ContentNodeType.LIST_UL);
            } else if (element.tagName().equals("ol")) {
                listNode = new ListNode<>(ContentNodeType.LIST_OL);
            } else listNode = new ListNode<>(ContentNodeType.LIST_DL);
            mapList(element, listNode);
            return listNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }

    private void mapList(Element element, ListNode<ContentNode<?>> listNode) {
        Elements children = element.children();
        for (Element child : children) {
            listNode.addElement(mapRow(child));
        }
    }

    private ContentNode<?> mapRow(Element row) {
        if (row.is("ul, ol, dl")) {
            return scrapElement(row);
        } else {
            List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(row, source);
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else {
                if (contentNodes.stream().allMatch(contentNode -> contentNode instanceof TextNode)) {
                    List<TextNode> textNodes = contentNodes.stream()
                            .map(contentNode -> (TextNode) contentNode).collect(Collectors.toList());
                    return new ParagraphNode(textNodes);
                } else return new ListNode<>(ContentNodeType.CONTENT_NODES, contentNodes);
            }
        }
    }

}
