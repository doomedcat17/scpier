package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;

import java.util.List;

public class LineMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        if (isNotSimpleLine(element)) {
            String title = extractTitle(element);
            appendix.setTitle(title);
        }
        if (!element.tagName().equals("strong")) {
            List<ContentNode<String>> contentNodes = textScrapper.scrapText(element);
            contentNodes.forEach(appendix::addContentNode);
        }
        return appendix;
    }

    private String extractTitle(Element element) {
        String title;
        if (!element.tagName().equals("strong")) {
            Element strongElement = element.selectFirst("strong");
            title = strongElement
                    .text().trim();
            strongElement.remove();
        } else title = element.text().trim();
        if (title.charAt(title.length() - 1) == ':')
            title = title.substring(0, title.length() - 1); //remove : from the end of title
        return title;
    }

    protected boolean isNotSimpleLine(Element element) {
        if (!element.parent().id().equals(PAGE_CONTENT_ID)) return false; //check
        if (!element.tagName().equals("strong")) {
            Node node = element.childNode(0); //some strong elements are inside text content of element
            if (node instanceof Element) element = (Element) node;
            else return false;
        }
        if (element.tagName().equals("strong")) {
            return isTittle(element.text());
        } else return false;
    }
}
