package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.div;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.List;

public abstract class DivMapperComponent extends DivMapper{

    abstract List<Appendix> mapDivContent(Element element);
}
