package com.doomedcat17.scpier.scrapper.htmlscrappers.table;

import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;

import java.util.ArrayList;
import java.util.List;

public class TableScrapper extends ElementScrapper {
    public TableScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
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
        appendix.addContentNode(mappedContentNode);
        return appendix;
    }

    private Appendix mapEnBaseTable(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<Appendix>> appendicesContentNodes = new ContentNode<>(ContentNodeType.APPENDICES, new ArrayList<>());
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        for (Element itemElement: itemHeaders.children()) {
            String[] splitElements = itemElement.text().split(":");
            Appendix innerAppendix = new Appendix();
            innerAppendix.setTitle(splitElements[0].trim());
            innerAppendix.addContentNode(new TextNode(splitElements[1].trim()));
            appendicesContentNodes.getContent().add(innerAppendix);
        }
        appendix.addContentNode(appendicesContentNodes);
        return appendix;
    }

    private Appendix mapItemInfo(Element element) {
        //TODO scp 139
        return null;
    }

    private ContentNode<?> mapRow(Element row) {
        List<ContentNode<List<ContentNode<?>>>> rowCells = new ArrayList<>();
        for (Element cell: row.children()) {
            ContentNode<List<ContentNode<?>>> cellNode = new ContentNode<>(ContentNodeType.TABLE_CELL, new ArrayList<>());
            if (cell.is("th")) cellNode.setContentNodeType(ContentNodeType.TABLE_HEADING_CELL);
            if(cell.children().isEmpty() && cell.text().isBlank()) continue;
            cellNode.getContent().addAll(scrapContent(cell));
            rowCells.add(cellNode);
        }
        return new ContentNode<>(ContentNodeType.TABLE_ROW, rowCells);
    }



}
