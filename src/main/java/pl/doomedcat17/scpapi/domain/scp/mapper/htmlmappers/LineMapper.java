package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.List;

public class LineMapper extends HtmlMapper {
//TODO more tests needed
    @Override
    public void mapElement(Element element, ScpObject scpObject) {
        Appendix appendix = new Appendix();
        DeletedContentMarker.markDeletedContent(element);
        if (isNotSimpleParagraph(element)) {
            String title = extractTitle(element);
            appendix.setTitle(title);
            addContent(appendix, element);
            scpObject.addAppendix(appendix);
        } else {
            addContent(scpObject.getLastAppendix(), element);
        }
    }

    private boolean isNotSimpleParagraph(Element element) {
        Element strongElement = element.selectFirst("strong");
        if(strongElement != null) {
            if (ScpPattern.containsValue(strongElement.text(), "eng")) {
                return true;
            } else {
                return (strongElement.text().length() > 20);
            }
        } else return false;
    }


    private String extractTitle(Element element) {
        Element strongElement = element.selectFirst("strong");
        String title = strongElement
                .text().trim();
        if (title.charAt(title.length() - 1) == ':')
            title = title.substring(0, title.length() - 1); //remove : from the end of title
        strongElement.remove();
        return title;
    }
    private void addContent(Appendix appendix, Element element) {
        List<ContentBox<String>> contentBoxes = scrapText(element);
        contentBoxes.forEach(appendix::addContentBox);
    }
}
