package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.data.Image;

import java.util.ArrayList;
import java.util.List;
//TODO refactor code
public class TableMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        contentNode.setContent(mapTable(element));
        if (isTableAnImage(contentNode)) {
            ContentNode<Image> imageContentNode = extractImage(contentNode);
            appendix.addContentNode(imageContentNode);
        } else {
            contentNode.setContentNodeType(ContentNodeType.TABLE);
            appendix.addContentNode(contentNode);
        }
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

    //TODO TEST NEEDED
    private ContentNode<Image> extractImage(ContentNode<List<ContentNode<?>>> contentNode) {
        List<ContentNode<?>> rows = contentNode.getContent();
        List<ContentNode<?>> rowContentNodes = (List<ContentNode<?>>) rows.get(0).getContent();
        ContentNode<Image> imageContentNode = (ContentNode<Image>) rowContentNodes.get(0);
        if (rows.size() == 2) {
            rowContentNodes = (List<ContentNode<?>>) rows.get(1).getContent();
            if (ContentNodeType.isText(rowContentNodes.get(0).getContentNodeType())) {
                imageContentNode.getContent().setCaption((String) rowContentNodes.get(0).getContent());
            }
        }
        return imageContentNode;
    }

    private boolean isTableAnImage(ContentNode<List<ContentNode<?>>> contentNode) {
        List<ContentNode<?>> rows = contentNode.getContent();
        if (rows.size() <= 2) {
            for (ContentNode<?> row: rows) {
                List<ContentNode<?>> rowContentNodes = (List<ContentNode<?>>) row.getContent();
                if (rowContentNodes.size() == 1) {
                    return rowContentNodes.get(0).getContentNodeType().equals(ContentNodeType.IMAGE);
                } else break;
            }
        }
        return false;
    }


}
