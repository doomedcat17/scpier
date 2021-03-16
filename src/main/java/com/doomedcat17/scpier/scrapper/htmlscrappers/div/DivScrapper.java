package com.doomedcat17.scpier.scrapper.htmlscrappers.div;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts.*;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts.FootnotesScrapper;

import java.util.ArrayList;
import java.util.List;

public class DivScrapper extends ElementScrapper {

    public DivScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix mappedAppendix;
        List<Appendix> appendices = mapContent(element);
        if (appendices.size() == 1) {
            mappedAppendix = appendices.get(0);
            if (mappedAppendix.getContents().size() > 1) {
                mappedAppendix.setContents(new ArrayList<>(List.of(new ContentNode<>(ContentNodeType.DIV, mappedAppendix.getContents()))));
            }
        } else {
            ContentNode<List<Appendix>> contentNode = new ContentNode<>(ContentNodeType.APPENDICES, appendices);
            mappedAppendix = new Appendix();
            mappedAppendix.addContentNode(contentNode);
        }
        return mappedAppendix;
    }

    protected List<Appendix> mapContent(Element element) {
        DivScrapperComponent divMapperComponent = getComponent(element);
        return divMapperComponent.scrapDivContent(element);
    }

    private DivScrapperComponent getComponent(Element element) {
        if (element.hasClass("collapsible-block")) {
            return new CollapsibleBlockScrapper(source, titleResolver);
        } else if (element.id().equals("u-adult-warning")) {
            return new AdultWarningScrapper(source, titleResolver);
        } else if (element.hasClass("footnotes-footer")) {
            return new FootnotesScrapper(source, titleResolver);
        } else if (element.hasClass("material-box")) {
            return new MaterialBoxScrapper(source, titleResolver);
        } else if (element.hasClass("anom-bar-container")) {
            return new AnomBarScrapper(source, titleResolver);
        } else if (element.hasClass("scp-image-block") || element.hasClass("image-container")){
            return new ImageBlockScrapper(source, titleResolver);
        } else if (element.hasClass("scale EN-base")){
            return new ENbaseDivScrapper(source, titleResolver);
        } else if (element.hasClass("acs-hybrid-text-bar")){
            return new ACSDivScrapper(source, titleResolver);
        } else if (element.hasClass("player-wrapper")){
            return new PlayerWrapperScrapper(source, titleResolver);
        } else if (element.hasClass("objclassbar")){
            return new ObjectClassBarDivScrapper(source, titleResolver);
        } else if (element.hasClass("yui-navset") || element.hasClass("yui-content")){
            return new YuiDivScrapper(source, titleResolver);
        } else return new DefaultDivScrapper(source, titleResolver);
    }
}
