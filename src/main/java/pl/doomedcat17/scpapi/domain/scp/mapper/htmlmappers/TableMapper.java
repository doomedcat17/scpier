package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;

public class TableMapper implements HtmlMapper<String[][]> {
    @Override
    public Appendix<String[][]> mapElement(Element element) {
        return null;
    }
}
