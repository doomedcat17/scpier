package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassBarDivScrapper extends DivScrapper implements DivScrapperComponent {
    public ObjectClassBarDivScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        List<Element> elements = new ArrayList<>();
        elements.add(element.getElementsByClass("sideleft").first());
        elements.add(element.getElementsByClass("sidemiddle").first());
        for (Element sideElement : elements) {
            contentNodes.addAll(scrapSide(sideElement));
        }
        return contentNodes;
    }

    private List<ContentNode<List<TextNode>>> scrapSide(Element sideElement) {
        List<ContentNode<List<TextNode>>> paragraphs = new ArrayList<>();
        sideElement.children().forEach(lineElement -> paragraphs.add(scrapLine(lineElement)));
        return paragraphs;
    }

    private ContentNode<List<TextNode>> scrapLine(Element lineElement) {
        List<TextNode> textNodes = TextScrapper.scrapText(lineElement, source);
        textNodes.get(0).addStyle("font-weight", "bold");
        if (!textNodes.get(0).getContent().endsWith(" ")) textNodes.get(0).setContent(textNodes.get(0).getContent()+" ");
        return new ContentNode<>(ContentNodeType.PARAGRAPH, textNodes);
    }
}