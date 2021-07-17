package com.doomedcat17.scpier.scrapper.table;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class TableScrapper extends ElementScrapper {
    public TableScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            return scrapTable(element);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }
    }

    private ContentNode<?> scrapTable(Element element)  {
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

    private ListNode<ContentNode<?>> scrapDefaultTable(Element element)  {
        List<ContentNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow: element.children()) {
            tableRows.add(scrapRow(tableRow));
        }
        return new ListNode<>(ContentNodeType.TABLE, tableRows);
    }

    private ContentNode<?> scrapEnBaseTable(Element element) {
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        ListNode<ParagraphNode> paragraphs = new ListNode<>(ContentNodeType.PARAGRAPHS);
        for (Element itemElement: itemHeaders.children()) {
            ParagraphNode paragraph = new ParagraphNode();
            String[] splitElements = itemElement.text().split(": ");
            TextNode strongNode = new TextNode(splitElements[0].trim()+": ");
            strongNode.addStyle("font-weight", "bold");
            strongNode.setContent(strongNode.getContent());
            paragraph.addElement(strongNode);
            paragraph.addElement(new TextNode(splitElements[1].trim()));
            paragraphs.addElement(paragraph);
        }
        return paragraphs;
    }

    private ContentNode<?> scrapRow(Element row)  {
        List<ListNode<ContentNode<?>>> rowCells = new ArrayList<>();
        for (Element cell: row.children()) {
            if(cell.children().isEmpty() && cell.text().isBlank()) continue;
            ListNode<ContentNode<?>> cellNode = new ListNode<>(ContentNodeType.TABLE_CELL);
            if (cell.is("th, thead")) cellNode.setContentNodeType(ContentNodeType.TABLE_HEADING_CELL);
            cellNode.addElements(ElementContentScrapper.scrapContent(cell, source));
            rowCells.add(cellNode);
        }
        return new ListNode<>(ContentNodeType.TABLE_ROW, rowCells);
    }



}
