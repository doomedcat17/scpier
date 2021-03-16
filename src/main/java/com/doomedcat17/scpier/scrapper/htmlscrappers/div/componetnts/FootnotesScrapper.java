package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;

public class FootnotesScrapper extends DivScrapper implements DivScrapperComponent {
    public FootnotesScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Appendix appendix = new Appendix();
        appendix.setTitle("Footnotes");
        Elements footnotes = element.select(".footnote-footer");
        for (Element footnote: footnotes) {
            ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            paragraph.getContent().addAll(TextScrapper.scrapText(footnote, source));
            appendix.addContentNode(paragraph);
        }
        return List.of(appendix);
    }
}
