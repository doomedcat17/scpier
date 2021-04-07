package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;


public class ENbaseDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ENbaseDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        String scpClassName = element.getElementsByClass("obj-text")
                .get(0)
                .text().trim();
        TextNode objectClassName = new TextNode(capitalizeText(scpClassName));
        TextNode objectClass = new TextNode("Object Class: ");
        objectClassName.addStyle("font-weight", "bold");
        objectClass.addStyle("font-weight", "bold");
        contentNodes.add(new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(objectClass, objectClassName))));
        return contentNodes;
    }

}
