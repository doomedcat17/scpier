package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.List;

//TODO tests needed
//TODO refactor code
public class ListMapper extends HtmlMapper {
    @Override
    public void mapElement(Element element, ScpObject scpObject) {
        ContentBox<String> contentBox = new ContentBox<>();
        contentBox.setContent(mapList(element, "", element.tagName()).trim());
        contentBox.setContentType(ContentType.TEXT_LIST);
        scpObject.getLastAppendix().addContentBox(contentBox);
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
