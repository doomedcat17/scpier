package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
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
        contentNodes.add(scrapItemName(element));
        contentNodes.add(scrapObjectName(element));
        return contentNodes;
    }

    private ContentNode<List<TextNode>> scrapItemName(Element element) {
        String itemText = element.getElementsByClass("itemnum").get(0).text();
        String[] itemStrings = itemText.split(":");
        TextNode itemHeading = new TextNode(itemStrings[0] + ": ");
        itemHeading.addStyle("font-weight", "bold");
        TextNode itemName = new TextNode(itemStrings[1].trim());
        return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(itemHeading, itemName)));
    }

    private ContentNode<List<TextNode>> scrapObjectName(Element element) {
        String scpClassName = element.selectFirst(".obj-text").text();
        TextNode objectClassName = new TextNode(capitalizeText(scpClassName));
        TextNode objectClass = new TextNode("Object Class: ");
        objectClass.addStyle("font-weight", "bold");
        return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(objectClass, objectClassName)));
    }

}
