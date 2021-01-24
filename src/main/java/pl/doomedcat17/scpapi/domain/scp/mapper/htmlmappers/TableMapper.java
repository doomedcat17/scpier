package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.data.Image;

import java.util.ArrayList;
import java.util.List;

//TODO refactor code and SPLIT mapTable
public class TableMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        return mapTable(element);
    }

    private Appendix mapTable(Element element) {
        Element tableBody = element.selectFirst("tbody");
        if (tableBody != null) {
            element = tableBody;
        }
        if (element.hasClass("scale EN-base") ||
                (element.is("tbody") &&
                        element.parent().hasClass("scale EN-base") &&
                        element.parent().is("table"))) {
            return mapEnBaseTable(element);
        } else return mapDefaultTable(element);
    }

    private Appendix mapDefaultTable(Element element) {
        Appendix appendix = new Appendix();
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow: element.children()) {
            tableRows.add(mapRow(tableRow));
        }
        ContentNode<List<ContentNode<?>>> mappedContentNode = new ContentNode<>(ContentNodeType.TABLE, tableRows);
        if (isTableAnImage(mappedContentNode)) {
            ContentNode<Image> imageContentNode = extractImage(mappedContentNode);
            appendix.addContentNode(imageContentNode);
        } else appendix.addContentNode(mappedContentNode);
        return appendix;
    }

    private Appendix mapEnBaseTable(Element element) {
        Appendix appendix = new Appendix();
        List<Appendix> appendices = new ArrayList<>();
        ContentNode<List<Appendix>> contentNode = new ContentNode<>(ContentNodeType.APPENDICES, appendices);
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        contentNodes.add(contentNode);
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        for (Element itemElement: itemHeaders.children()) {
            String[] splitElements = itemElement.text().split(":");
            Appendix innerAppendix = new Appendix();
            innerAppendix.setTitle(splitElements[0].trim());
            innerAppendix.addContentNode(new ContentNode<>(ContentNodeType.TEXT, splitElements[1].trim()));
            appendices.add(innerAppendix);
        }
        appendix.addContentNode(contentNode);
        return appendix;
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
