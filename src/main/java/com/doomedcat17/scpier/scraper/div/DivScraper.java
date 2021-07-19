package com.doomedcat17.scpier.scraper.div;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.ListNode;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.div.componetnts.*;
import org.jsoup.nodes.Element;

import java.util.List;

public class DivScraper extends ElementScraper {

    public DivScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            List<ContentNode<?>> contentNodes = scrap(element);
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else return new ListNode<>(ContentNodeType.CONTENT_NODES, contentNodes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }
    }

    protected List<ContentNode<?>> scrap(Element element)  {
        DivScraperComponent divMapperComponent = getDivScrapperComponent(element);
        return divMapperComponent.scrapDivContent(element);
    }

    private DivScraperComponent getDivScrapperComponent(Element element) {
        if (element.hasClass("collapsible-block") || element.hasClass("colmod-block")) {
            return new CollapsibleBlockScraper(source);
        } else if (element.id().equals("u-adult-warning")) {
            return new AdultWarningScraper(source);
        } else if (element.hasClass("footnotes-footer")) {
            return new FootnotesScraper(source);
        } else if (element.hasClass("material-box")) {
            return new MaterialBoxScraper(source);
        } else if (element.hasClass("anom-bar-container")) {
            return new AnomBarScraper(source);
        } else if (element.hasClass("scp-image-block") || element.hasClass("image-container")) {
            return new ImageBlockScraper(source);
        } else if (element.hasClass("scale")) {
            return new ENbaseDivScraper(source);
        } else if (element.hasClass("acs-hybrid-text-bar")) {
            return new ACSDivScraper(source);
        } else if (element.hasClass("player-wrapper")) {
            return new PlayerWrapperScraper(source);
        } else if (element.hasClass("objclassbar")) {
            return new ObjectClassBarDivScraper(source);
        } else if (element.hasClass("yui-navset") || element.hasClass("yui-content")) {
            return new YuiDivScraper(source);
        } else return new DefaultDivScraper(source);
    }
}
