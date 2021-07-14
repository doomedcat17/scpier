package com.doomedcat17.scpier.scrapper.list;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ListScrapper extends ElementScrapper {
    public ListScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
            contentNode.setContent(new ArrayList<>());
            if (element.tagName().equals("ul")) {
                contentNode.setContentNodeType(ContentNodeType.LIST_UL);
            } else if (element.tagName().equals("dl")) {
                contentNode.setContentNodeType(ContentNodeType.LIST_DL);
            } else contentNode.setContentNodeType(ContentNodeType.LIST_OL);
            mapList(element, contentNode);
            return contentNode;
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }

    private void mapList(Element element, ContentNode<List<ContentNode<?>>> contentNode) {
        Elements children = element.children();
        for (Element child : children) {
            contentNode.getContent().add(mapRow(child));
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
                    return new ContentNode<>(ContentNodeType.PARAGRAPH, contentNodes);
                } else return new ContentNode<>(ContentNodeType.CONTENT_NODES, contentNodes);
            }
        }
    }

}
