package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentType;

import java.util.List;

//TODO tests needed
public class ListMapper implements HtmlMapper<String> {
    @Override
    public Appendix<String> mapElement(Element element) {
        Appendix<String> appendix = new Appendix<>();
        appendix.setContent(mapList(element, "", element.tagName()).trim());
        appendix.setContentType(ContentType.TEXT);
        return appendix;
    }

    private String mapList(Element element, String prefix, String listType) {
        // counter for ordered list
        int counter = 1;
        if (listType.equals("ul")) counter = 0; //disable counter
        StringBuilder stringBuilder = new StringBuilder();
        Elements children = element.children();
        for (Element row: children) {
            appendLine(stringBuilder, mapRow(row, prefix), prefix, counter);
            if (counter != 0) counter++;

        }
        return stringBuilder.toString();
    }

    private String mapRow(Element row, String prefix) {
        if (row.is("ul, ol")) {
            return row.ownText()+mapList(row, '\t'+prefix, row.tagName());
        } else {
            List<Node> rowChildren = row.childNodes();
            //check and map inner elements
            if (!rowChildren.isEmpty()) {
                StringBuilder content = new StringBuilder();
                for (Node child : rowChildren) {
                    if (child.toString().isBlank()) continue;
                    if (child instanceof Element) {
                        Element childElement = (Element) child;
                        DeletedContentMarker.markDeletedContent(childElement);
                        if (childElement.is("ul, ol")) {
                            content.append('\n');
                            content.append(mapList(childElement, '\t' + prefix, childElement.tagName()));
                        } else content.append(childElement.text());
                    } else content.append(child.toString());
                }
                return content.toString();
            } else return row.text();
        }

    }
    private void appendLine(StringBuilder stringBuilder, String text, String prefix, int counter) {
        //check if line haven't already prefixed
        if (text.charAt(0) != '\t') {
            stringBuilder
                    .append(prefix);
            if (counter != 0) {
                stringBuilder
                        .append(counter)
                        .append('.');
            } else stringBuilder.append('•');
            stringBuilder
                    .append(' ');
        }
                    stringBuilder.append(text);
            if (!(stringBuilder.charAt(stringBuilder.length() - 1) == '\n')) stringBuilder.append('\n');
    }

}
