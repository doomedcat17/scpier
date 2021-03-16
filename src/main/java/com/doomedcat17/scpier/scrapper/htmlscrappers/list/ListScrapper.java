package com.doomedcat17.scpier.scrapper.htmlscrappers.list;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.TextNode;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

public class ListScrapper extends ElementScrapper {
    public ListScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        contentNode.setContent(new ArrayList<>());
        if (element.tagName().equals("ul")) {
            contentNode.setContentNodeType(ContentNodeType.LIST_UL);
        } else contentNode.setContentNodeType(ContentNodeType.LIST_OL);
        mapList(element, contentNode);
        appendix.addContentNode(contentNode);
        return appendix;
    }

    private void mapList(Element element, ContentNode<List<ContentNode<?>>> contentNode) {
        Elements children = element.children();
        children.forEach(
                child -> contentNode.getContent().add(mapRow(child))
        );
    }

    private ContentNode<?> mapRow(Element row) {
        if (row.is("ul, ol")) {
            Appendix nestedAppendix = scrapElement(row);
            return nestedAppendix.getContents().get(0);

        } else {
            List<ContentNode<?>> contentNodes = scrapElementInnerContent(row);
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else {
                if (contentNodes.stream().allMatch(contentNode -> contentNode instanceof TextNode)) {
                    return new ContentNode<>(ContentNodeType.PARAGRAPH, contentNodes);
                } else return new ContentNode<>(ContentNodeType.ELEMENTS, contentNodes);
            }
        }
    }

}
