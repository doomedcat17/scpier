package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.HtmlScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts.FootnotesScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts.*;

import java.util.ArrayList;
import java.util.List;

public class DivScrapper extends HtmlScrapper {
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
            return new CollapsibleBlockScrapper();
        } else if (element.hasClass("footnotes-footer")) {
            return new FootnotesScrapper();
        } else if (element.hasClass("anom-bar-container")) {
            return new AnomBarScrapper();
        } else if (element.hasClass("scp-image-block") || element.hasClass("image-container")){
            return new ImageBlockScrapper();
        } else if (element.hasClass("scale EN-base")){
            return new ENbaseDivScrapper();
        } else if (element.hasClass("acs-hybrid-text-bar")){
            return new ACSDivScrapper();
        } else if (element.hasClass("objclassbar")){
            return new ObjectClassBarDivScrapper();
        } else return new DefaultDivScrapper();
    }
}
