package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentType;

public class LineMapper implements HtmlMapper<String> {
//TODO more tests needed
    @Override
    public Appendix<String> mapElement(Element element) {
        Appendix<String> appendix = new Appendix<>();
        Elements deletedTextElements = element.select("span[style*=\"text-decoration: line-through;\"]");
        if (!deletedTextElements.isEmpty()) {
            for (Element deletedText : deletedTextElements) {
                deletedText.prepend(Patterns.DELETED_TEXT_MARK).append(Patterns.DELETED_TEXT_MARK);
            }
        }
        if (isNotSimpleParagraph(element)) {
            String[] extractedData = extractTitleAndText(element);
            element.select("span");
            appendix.setTitle(extractedData[0]);
            appendix.setContent(extractedData[1]);
        } else appendix.setContent(element.text().trim());
        appendix.setContentType(ContentType.TEXT);
        return appendix;
    }

    private boolean isNotSimpleParagraph(Element element) {
        Element strongElement = element.selectFirst("strong");
        if(strongElement != null) {
            if (strongElement.text().contains(Patterns.SCP_CLASS_PATTERN) ||
                    element.text().contains(Patterns.SCP_NAME_PATTERN) ||
                    element.text().contains(Patterns.SCP_DESCRIPTION_PATTERN) ||
                    element.text().contains(Patterns.SCP_PROCEDURES_PATTERN)
            ) {
                return true;
            } else {
                return (strongElement.text().length() > 20);
            }
        } else return false;
    }


    private String[] extractTitleAndText(Element element) {
        String title = element
                .selectFirst("strong")
                .text().trim();
        String content = element
                .text()
                .substring(title.length())
                .trim();
        if (title.charAt(title.length() - 1) == ':')
            title = title.substring(0, title.length() - 1); //remove : from the end of title
        return new String[]{title, content};
    }
}
