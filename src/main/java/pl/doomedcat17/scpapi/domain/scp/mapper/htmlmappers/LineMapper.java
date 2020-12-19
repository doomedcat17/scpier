package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.List;

public class LineMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        if (isNotSimpleElement(element)) {
            String title = extractTitle(element);
            appendix.setTitle(title);
        }
        List<ContentNode<String>> contentNodes = scrapText(element);
        contentNodes.forEach(appendix::addContentNode);
        return appendix;
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
}
