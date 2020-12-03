package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentType;

public class BlockquoteMapper implements HtmlMapper<String> {
    @Override
    public Appendix<String> mapElement(Element element) {
        Appendix<String> appendix = new Appendix<>();
        String content = element.text();
        appendix.setContent(content);
        return appendix;
    }
}
