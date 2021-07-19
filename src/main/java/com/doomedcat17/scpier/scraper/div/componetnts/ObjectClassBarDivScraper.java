package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ParagraphNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ObjectClassBarDivScraper extends DivScraper implements DivScraperComponent {
    public ObjectClassBarDivScraper(String source) {
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

    private List<ParagraphNode> scrapSide(Element sideElement) {
        List<ParagraphNode> paragraphs = new ArrayList<>();
        sideElement.children().forEach(lineElement -> paragraphs.add(scrapLine(lineElement)));
        return paragraphs;
    }

    private ParagraphNode scrapLine(Element lineElement) {
        List<TextNode> textNodes = TextScraper.scrapText(lineElement, source);
        textNodes.get(0).addStyle("font-weight", "bold");
        if (!textNodes.get(0).getContent().endsWith(" ")) textNodes.get(0).setContent(textNodes.get(0).getContent()+" ");
        return new ParagraphNode(textNodes);
    }
}
