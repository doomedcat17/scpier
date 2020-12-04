package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;

import java.util.List;

public class BlockquoteMapper implements HtmlMapper {
    @Override
    public void mapElement(Element element, List<Appendix<?>> scpAppendices) {
        ContentBox<String> contentBox = new ContentBox<>();
        DeletedContentMarker.markDeletedContent(element);
        String content = element.text().trim();
        contentBox.setContent(content);
        contentBox.setContentType(ContentType.TEXT);
        scpAppendices.get(scpAppendices.size() - 1).addContentBox(contentBox);
    }
}
