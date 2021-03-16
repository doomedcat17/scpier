package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivScrapper extends DivScrapper implements DivScrapperComponent {

    public DefaultDivScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = scrapElementInnerContent(element);
        Appendix appendix = new Appendix();
        appendix.setContents(contentNodes);
        return new ArrayList<>(List.of(appendix));
    }
}
