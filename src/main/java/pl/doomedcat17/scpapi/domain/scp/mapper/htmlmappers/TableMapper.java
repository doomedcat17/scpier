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
        appendix.addContentBox(contentNode);
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

    private ContentNode<List<ContentNode<?>>> mapRow(Element row) {
        List<ContentNode<?>> rowCells = new ArrayList<>();
        for (Element cell: row.children()) {
            ContentNode<List<ContentNode<?>>> cellNode = new ContentNode<>(ContentNodeType.ELEMENTS);
            cellNode.setContent(extractContent(cell));
            rowCells.add(cellNode);
        }
        return new ContentNode<>(ContentNodeType.ROW, rowCells);
    }


}
