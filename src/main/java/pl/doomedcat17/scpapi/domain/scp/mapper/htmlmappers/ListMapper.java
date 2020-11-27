package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentType;

//TODO tests needed
public class ListMapper implements HtmlMapper<String> {
    @Override
    public Appendix<String> mapElement(Element element) {
        Appendix<String> appendix = new Appendix<>();
        appendix.setContent(mapList(element, ""));
        appendix.setContentType(ContentType.TEXT);
        return appendix;
    }

    private String mapList(Element element, String prefix) {
        if (element.tagName().equals("ul")) {
            return mapUnorderedList(element, prefix);
        } else return mapOrderedList(element, prefix);
    }


    private String mapUnorderedList(Element element, String prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element row: element.children()) {
            if (row.tagName().equals("ul") || row.tagName().equals("ol")) {
                stringBuilder.append(mapList(row, "   "+prefix));
                continue;
            }
            stringBuilder
                    .append('\n')
                    .append(prefix)
                    .append('•')
                    .append(' ')
                    .append(row.text());
        }
        stringBuilder.deleteCharAt(0);
        return stringBuilder.toString();
    }

    private String mapOrderedList(Element element, String prefix) {
        StringBuilder stringBuilder = new StringBuilder();
        Elements children = element.children();
        for (int i = 1; i < children.size()+1; i++) {
            Element row = children.get(i);
            if (row.tagName().equals("ul") || row.tagName().equals("ol")) {
                stringBuilder.append(mapList(row, "   "+prefix));
                continue;
            }
            stringBuilder
                    .append('\n')
                    .append(prefix)
                    .append(i)
                    .append(' ')
                    .append(row.text());
        }
        stringBuilder.deleteCharAt(0);
        return stringBuilder.toString();
    }
}
