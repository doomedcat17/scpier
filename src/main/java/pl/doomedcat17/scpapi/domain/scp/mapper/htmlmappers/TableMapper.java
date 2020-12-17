package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.ArrayList;
import java.util.List;
//TODO refactor code
public class TableMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<List<String>>> contentNode = new ContentNode<>();
        contentNode.setContent(mapTable(element));
        contentNode.setContentNodeType(ContentNodeType.TABLE);
        appendix.addContentBox(contentNode);
        return appendix;
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
            mappedRow.add(element.text().trim());
        }
        return mappedRow;
    }

}
