package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FootnotesScrapper extends DivScrapper implements DivScrapperComponent {
    public FootnotesScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        ContentNode<List<TextNode>> headingParagraph = new ContentNode<>(ContentNodeType.HEADING, new ArrayList<>());
        headingParagraph.getContent().add(new TextNode("Footnotes"));
        contentNodes.add(headingParagraph);
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            paragraph.getContent().addAll(TextScrapper.scrapText(footnote, source));
            contentNodes.add(paragraph);
        }
        return contentNodes;
    }
}
