package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class CollapsibleBlockMapper extends DivMapperComponent {
    @Override
    List<Appendix> mapDivContent(Element element) {
        Appendix appendix = new Appendix();
        String title = element.selectFirst(".collapsible-block-link").text();
        title = clearCollapsibleBlockTittle(title);
        if (isTittle(title)) appendix.setTitle(title);
        Element collapsibleBlockContent = element.selectFirst(".collapsible-block-content");
        List<ContentNode<?>> contentNodes = mapElementContent(collapsibleBlockContent);
        appendix.setContents(contentNodes);
        if (!appendix.hasTitle()) {
            appendix.setTitle(lookForTittle(contentNodes));
        }
        return new ArrayList<>(List.of(appendix));
    }

    private String clearCollapsibleBlockTittle(String title){
        char firstChar = title.charAt(0);
        if (firstChar == '+' || firstChar == '>' || firstChar == '-') {
            return title.substring(1).trim();
        } else return title;
    }
}
