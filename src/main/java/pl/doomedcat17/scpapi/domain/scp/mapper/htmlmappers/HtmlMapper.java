package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;

public interface HtmlMapper<T> {

    Appendix<T> mapElement(Element element);
}
