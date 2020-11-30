package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeletedContentMarker {

    public static final String DELETED_TEXT_MARK = "[DELETED]";

    public static void markDeletedContent(Element element) {
        Elements deletedTextElements = element.select("span[style*=\"text-decoration: line-through;\"]");
        if (!deletedTextElements.isEmpty()) {
            for (Element deletedText : deletedTextElements) {
                deletedText.prepend(DELETED_TEXT_MARK).append(DELETED_TEXT_MARK);
                deletedText.removeAttr("style");
            }
        }
    }
}
