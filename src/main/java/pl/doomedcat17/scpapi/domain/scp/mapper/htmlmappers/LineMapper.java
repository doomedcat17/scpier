package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class LineMapper implements HtmlMapper {

    @Override
    public Map<String, String> mapElement(Element element) {
        Map<String, String> elementData = new HashMap<>();
        Elements deletedTextElements = element.select("span[style*=\"text-decoration: line-through;\"]");
        if (!deletedTextElements.isEmpty()) {
            for (Element deletedText : deletedTextElements) {
                deletedText.prepend(Patterns.DELETED_TEXT_MARK).append(Patterns.DELETED_TEXT_MARK);
            }
        }
        if (isNotSimpleParagraph(element)) {
            String[] extractedData = extractTitleAndText(element);
            element.select("span");
            elementData.put(
                    "title",
                    extractedData[0]);
            elementData.put(
                    "content",
                    extractedData[1]);
        } else elementData.put(
                "content",
                element.text().trim());
        return elementData;
    }

    private boolean isNotSimpleParagraph(Element element) {
        if (element.text().contains(Patterns.SCP_CLASS_PATTERN) ||
                element.text().contains(Patterns.SCP_NAME_PATTERN) ||
                element.text().contains(Patterns.SCP_DESCRIPTION_PATTERN) ||
                element.text().contains(Patterns.SCP_PROCEDURES_PATTERN)
        ) {
            return true;
        } else {
            return (!element.select("strong").isEmpty() && element.text().length() > 100);
        }
    }


    private String[] extractTitleAndText(Element element) {
        String title = element
                .select("strong")
                .first()
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
