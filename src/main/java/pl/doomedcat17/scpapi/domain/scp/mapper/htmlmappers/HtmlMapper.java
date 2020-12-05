package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.ScpObject;

public interface HtmlMapper {

    void mapElement(Element element, ScpObject scpObject);
}

