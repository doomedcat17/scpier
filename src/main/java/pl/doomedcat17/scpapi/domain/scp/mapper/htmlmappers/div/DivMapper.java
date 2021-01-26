package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.*;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.HtmlMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DivMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
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
        DivMapperComponent divMapperComponent = getComponent(element);
        return divMapperComponent.mapDivContent(element);
    }

    private DivMapperComponent getComponent(Element element) {
        if (element.hasClass("collapsible-block")) {
            return new CollapsibleBlockMapper();
        } else if (element.hasClass("footnotes-footer")) {
            return new FootnotesMapper();
        } else if (element.hasClass("anom-bar-container")) {
            return new AnomBarMapper();
        } else if (element.hasClass("scp-image-block") || element.hasClass("image-container")){
            return new ImageBlockMapper();
        } else if (element.hasClass("scale EN-base")){
            return new ENbaseDivMapper();
        } else if (element.hasClass("acs-hybrid-text-bar")){
            return new ACSDivMapper();
        } else if (element.hasClass("objclassbar")){
            return new ObjectClassBarDivMapper();
        } else return new DefaultDivMapper();
    }
}
