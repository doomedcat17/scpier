package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

public class BlockquoteMapper implements HtmlMapper {
    @Override
    public void mapElement(Element element, ScpObject scpObject) {
        ContentBox<String> contentBox = new ContentBox<>();
        DeletedContentMarker.markDeletedContent(element);
        String content = element.text().trim();
        contentBox.setContent(content);
        contentBox.setContentType(ContentType.TEXT);
        scpObject.getLastAppendix().addContentBox(contentBox);
    }
}
