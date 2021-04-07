package com.doomedcat17.scpier.scrapper.list;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
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
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        contentNode.setContent(new ArrayList<>());
        if (element.tagName().equals("ul")) {
            contentNode.setContentNodeType(ContentNodeType.LIST_UL);
        } else contentNode.setContentNodeType(ContentNodeType.LIST_OL);
        mapList(element, contentNode);
        return contentNode;
    }

    private void mapList(Element element, ContentNode<List<ContentNode<?>>> contentNode) {
        Elements children = element.children();
        children.forEach(
                child -> contentNode.getContent().add(mapRow(child))
        );
    }

    private ContentNode<?> mapRow(Element row) {
        if (row.is("ul, ol")) {
            return scrapElement(row);
        } else {
            List<ContentNode<?>> contentNodes = scrapContent(row);
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
