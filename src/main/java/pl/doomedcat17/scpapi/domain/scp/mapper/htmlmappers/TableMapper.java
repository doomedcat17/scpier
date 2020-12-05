package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

import java.util.ArrayList;
import java.util.List;

public class TableMapper implements HtmlMapper {
    @Override
    public void mapElement(Element element, ScpObject scpObject) {
        ContentBox<List<List<String>>> contentBox = new ContentBox<>();
        contentBox.setContent(mapTable(element));
        contentBox.setContentType(ContentType.TABLE);
        scpObject.getLastAppendix().addContentBox(contentBox);
    }

    private List<List<String>> mapTable(Element element) {
        Element table = element.selectFirst("tbody");
        List<List<String>> tableList = new ArrayList<>();
        for (Element tableRow: table.children()) {
            tableList.add(mapRow(tableRow));
        }
        return tableList;
    }
    private List<String> mapRow(Element row) {
        List<String> mappedRow = new ArrayList<>();
        for (Element element: row.children()) {
            DeletedContentMarker.markDeletedContent(element);
            mappedRow.add(element.text().trim());
        }
        return mappedRow;
    }

}
