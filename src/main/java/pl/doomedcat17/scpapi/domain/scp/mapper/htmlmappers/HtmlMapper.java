package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;

import java.util.Map;

public interface HtmlMapper {

    Map<String, String> mapElement(Element element);
}
