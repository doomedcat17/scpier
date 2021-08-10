package com.doomedcat17.scpier.scraper.table;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TableScraper extends ElementScraper {
    public TableScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            return scrapTable(element);
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }

    private ContentNode<?> scrapTable(Element element) {
        Element tableBody = element.selectFirst("tbody");
        if (tableBody != null) {
            element = tableBody;
        }

        if (element.hasClass("scale EN-base") ||
                (element.is("tbody") &&
                        element.parent().hasClass("scale EN-base") &&
                        element.parent().is("table"))) {
            return scrapEnBaseTable(element);
        } else if (element.is(".responsive_table")) {
            return scrapResponsiveTable(element);
        } else return scrapDefaultTable(element);
    }

    private ListNode<ContentNode<?>> scrapDefaultTable(Element element) {
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow : element.children()) {
            tableRows.add(scrapRow(tableRow));
        }
        return new ListNode<>(ContentNodeType.TABLE, tableRows);
    }

    private ContentNode<?> scrapEnBaseTable(Element element) {
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        ListNode<ParagraphNode> paragraphs = new ListNode<>(ContentNodeType.PARAGRAPHS);
        for (Element itemElement : itemHeaders.children()) {
            ParagraphNode paragraph = new ParagraphNode();
            String[] splitElements = itemElement.text().split(": ");
            TextNode strongNode = new TextNode(splitElements[0].trim() + ": ");
            strongNode.addStyle("font-weight", "bold");
            strongNode.setContent(strongNode.getContent());
            paragraph.addElement(strongNode);
            paragraph.addElement(new TextNode(splitElements[1].trim()));
            paragraphs.addElement(paragraph);
        }
        return paragraphs;
    }

    private ContentNode<?> scrapResponsiveTable(Element table) {
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow : table.children()) {
            tableRows.add(scrapResponsiveTableRow(tableRow));
        }
        return new ListNode<>(ContentNodeType.TABLE, tableRows);
    }

    private ContentNode<?> scrapResponsiveTableRow(Element row) {
        List<ListNode<ContentNode<?>>> rowCells = new ArrayList<>();
        if (row.is(".table_header")) {
            row = row.selectFirst(".table_row");
        }
        for (Element cell : row.children()) {
            if (cell.children().isEmpty() && cell.text().isBlank()) continue;
            ListNode<ContentNode<?>> cellNode = new ListNode<>(ContentNodeType.TABLE_CELL);
            if (cell.is(".table_header_data")) cellNode.setContentNodeType(ContentNodeType.TABLE_HEADING_CELL);
            cellNode.addElements(ElementContentScraper.scrapContent(cell, source));
            rowCells.add(cellNode);
        }
        return new ListNode<>(ContentNodeType.TABLE_ROW, rowCells);
    }

    private ContentNode<?> scrapRow(Element row) {
        List<ListNode<ContentNode<?>>> rowCells = new ArrayList<>();
        for (Element cell : row.children()) {
            if (cell.children().isEmpty() && cell.text().isBlank()) continue;
            ListNode<ContentNode<?>> cellNode = new ListNode<>(ContentNodeType.TABLE_CELL);
            if (cell.is("th, thead")) cellNode.setContentNodeType(ContentNodeType.TABLE_HEADING_CELL);
            cellNode.addElements(ElementContentScraper.scrapContent(cell, source));
            rowCells.add(cellNode);
        }
        return new ListNode<>(ContentNodeType.TABLE_ROW, rowCells);
    }


}
