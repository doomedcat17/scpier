package com.doomedcat17.scpier.scrapper.list;

import com.doomedcat17.scpier.data.content.*;
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
            e.printStackTrace();
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
        ListNode<ContentNode<?>> listItem = new ListNode<>(ContentNodeType.LIST_ITEM);
        if (row.is("ul, ol, dl")) {
            listItem.addElement(scrapElement(row));
        } else {
            List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(row, source);
            if (contentNodes.size() == 1) {
                listItem.addElement(contentNodes.get(0));
            } else {
                if (contentNodes.stream().allMatch(contentNode -> contentNode instanceof TextNode)) {
                    List<TextNode> textNodes = contentNodes.stream()
                            .map(contentNode -> (TextNode) contentNode).collect(Collectors.toList());
                    ParagraphNode paragraphNode = new ParagraphNode(textNodes);
                    listItem.addElement(paragraphNode);
                } else listItem.addElements(contentNodes);
            }
        }
        return listItem;
    }

}
