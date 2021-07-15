package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.HeadingNode;
import com.doomedcat17.scpier.data.contentnode.ParagraphNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FootnotesScrapper extends DivScrapper implements DivScrapperComponent {
    public FootnotesScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        HeadingNode headingParagraph = new HeadingNode();
        headingParagraph.addElement(new TextNode("Footnotes"));
        contentNodes.add(headingParagraph);
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            ParagraphNode paragraph = new ParagraphNode();
            paragraph.addElements(TextScrapper.scrapText(footnote, source));
            contentNodes.add(paragraph);
        }
        return contentNodes;
    }
}
