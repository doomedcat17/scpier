package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class DefaultDivMapper extends DivMapperComponent {

    @Override
    List<Appendix> mapDivContent(Element element) {
        List<ContentNode<?>> contentNodes = mapElementContent(element);
        Appendix appendix = new Appendix();
        appendix.setContents(contentNodes);
        appendix.setTitle(lookForTittle(contentNodes));
        return new ArrayList<>(List.of(appendix));
    }
}
