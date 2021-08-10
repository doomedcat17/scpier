package com.doomedcat17.scpier.scraper.div;

import com.doomedcat17.scpier.scraper.div.componetnts.*;
import com.doomedcat17.scpier.scraper.div.componetnts.anom.AnomBarScraper;
import org.jsoup.nodes.Element;

public class DivScraperComponentFactory {

    protected static DivScraperComponent getDivScrapperComponent(Element element, String source) {
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
        } else if (element.is(".player-wrapper, .widget")) {
            return new PlayerWrapperScraper(source);
        } else if (element.hasClass("objclassbar")) {
            return new ObjectClassBarDivScraper(source);
        } else if (element.hasClass("yui-navset") || element.hasClass("yui-content")) {
            return new YuiDivScraper(source);
        } else return new DefaultDivScraper(source);
    }
}
