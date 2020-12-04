package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.List;

public interface HtmlMapper {

    void mapElement(Element element, List<Appendix> scpAppendices);
}
