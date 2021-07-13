package com.doomedcat17.scpier.scrapper.div;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import com.doomedcat17.scpier.scrapper.div.componetnts.*;
import org.jsoup.nodes.Element;

import java.util.List;

public class DivScrapper extends ElementScrapper {

    public DivScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        List<ContentNode<?>> contentNodes = scrap(element);
        if (contentNodes.size() == 1) {
            return contentNodes.get(0);
        } else return new ContentNode<>(ContentNodeType.CONTENT_NODES, contentNodes);
    }

    protected List<ContentNode<?>> scrap(Element element) {
        DivScrapperComponent divMapperComponent = getDivScrapperComponent(element);
        return divMapperComponent.scrapDivContent(element);
    }

    private DivScrapperComponent getDivScrapperComponent(Element element) {
        if (element.hasClass("collapsible-block") || element.hasClass("colmod-block")) {
            return new CollapsibleBlockScrapper(source);
        } else if (element.id().equals("u-adult-warning")) {
            return new AdultWarningScrapper(source);
        } else if (element.hasClass("footnotes-footer")) {
            return new FootnotesScrapper(source);
        } else if (element.hasClass("material-box")) {
            return new MaterialBoxScrapper(source);
        } else if (element.hasClass("anom-bar-container")) {
            return new AnomBarScrapper(source);
        } else if (element.hasClass("scp-image-block") || element.hasClass("image-container")){
            return new ImageBlockScrapper(source);
        } else if (element.hasClass("scale")){
            return new ENbaseDivScrapper(source);
        } else if (element.hasClass("acs-hybrid-text-bar")){
            return new ACSDivScrapper(source);
        } else if (element.hasClass("player-wrapper")){
            return new PlayerWrapperScrapper(source);
        } else if (element.hasClass("objclassbar")){
            return new ObjectClassBarDivScrapper(source);
        } else if (element.hasClass("yui-navset") || element.hasClass("yui-content")){
            return new YuiDivScrapper(source);
        } else return new DefaultDivScrapper(source);
    }
}
