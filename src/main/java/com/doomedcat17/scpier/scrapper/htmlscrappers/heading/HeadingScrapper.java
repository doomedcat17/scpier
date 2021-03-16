package com.doomedcat17.scpier.scrapper.htmlscrappers.heading;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;

import java.util.List;

public class HeadingScrapper extends ElementScrapper {
    public HeadingScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<TextNode>> headingContentNode = new ContentNode<>(ContentNodeType.HEADING);
        List<TextNode> textNodes = TextScrapper.scrapText(element, source);
        headingContentNode.setContent(textNodes);
        if (element.parent().is("#page-content")) {
            appendix.setTitle(TextScrapper.margeTextNodes(textNodes));
        } else appendix.addContentNode(headingContentNode);
        return appendix;
    }
}
