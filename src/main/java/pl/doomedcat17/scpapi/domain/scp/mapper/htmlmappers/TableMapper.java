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
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        contentNode.setContent(mapTable(element));
        contentNode.setContentNodeType(ContentNodeType.TABLE);
        appendix.addContentNode(contentNode);
        return appendix;
    }

    private List<ContentNode<?>> mapTable(Element element) {
        Element tableBody = element.selectFirst("tbody");
        if (tableBody != null) element = tableBody;
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow: element.children()) {
            tableRows.add(mapRow(tableRow));
        }
        return tableRows;
    }

    private ContentNode<?> mapRow(Element row) {
        List<ContentNode<?>> rowCells = new ArrayList<>();
        for (Element cell: row.children()) {
            if(cell.children().size() == 0 && cell.text().isBlank()) continue;
            List<ContentNode<?>> extractedContentNodes = margeTextNodes(mapElementContent(cell));
            if (extractedContentNodes.size() == 1) {
                rowCells.add(extractedContentNodes.get(0));
            } else {
                rowCells.add(new ContentNode<>(ContentNodeType.ELEMENTS, extractedContentNodes));
            }
        }
        return new ContentNode<>(ContentNodeType.ROW, rowCells);
    }


}
