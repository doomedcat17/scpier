package com.doomedcat17.scpier.scrapper.table;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class TableScrapper extends ElementScrapper {
    public TableScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        return mapTable(element);
    }

    private ContentNode<?> mapTable(Element element) {
        Element tableBody = element.selectFirst("tbody");
        if (tableBody != null) {
            element = tableBody;
        }
        if (element.hasClass("scale EN-base") ||
                (element.is("tbody") &&
                        element.parent().hasClass("scale EN-base") &&
                        element.parent().is("table"))) {
            return scrapEnBaseTable(element);
        } else return scrapDefaultTable(element);
    }

    private ContentNode<List<ContentNode<?>>> scrapDefaultTable(Element element) {
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow: element.children()) {
            tableRows.add(mapRow(tableRow));
        }
        return new ContentNode<>(ContentNodeType.TABLE, tableRows);
    }

    private ContentNode<?> scrapEnBaseTable(Element element) {
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        ContentNode<List<ContentNode<List<TextNode>>>> paragraphs = new ContentNode<>(ContentNodeType.PARAGRAPHS, new ArrayList<>());
        for (Element itemElement: itemHeaders.children()) {
            ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            String[] splitElements = itemElement.text().split(": ");
            TextNode strongNode = new TextNode(splitElements[0].trim()+": ");
            strongNode.addStyle("font-weight", "bold");
            strongNode.setContent(strongNode.getContent());
            paragraph.getContent().add(strongNode);
            paragraph.getContent().add(new TextNode(splitElements[1].trim()));
            paragraphs.getContent().add(paragraph);
        }
        return paragraphs;
    }

    private ContentNode<?> mapRow(Element row) {
        List<ContentNode<List<ContentNode<?>>>> rowCells = new ArrayList<>();
        for (Element cell: row.children()) {
            ContentNode<List<ContentNode<?>>> cellNode = new ContentNode<>(ContentNodeType.TABLE_CELL, new ArrayList<>());
            if (cell.is("th, thead")) cellNode.setContentNodeType(ContentNodeType.TABLE_HEADING_CELL);
            if(cell.children().isEmpty() && cell.text().isBlank()) continue;
            cellNode.getContent().addAll(scrapContent(cell));
            rowCells.add(cellNode);
        }
        return new ContentNode<>(ContentNodeType.TABLE_ROW, rowCells);
    }



}
