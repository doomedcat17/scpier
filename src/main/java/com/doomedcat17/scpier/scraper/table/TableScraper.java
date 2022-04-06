package com.doomedcat17.scpier.scraper.table;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class TableScraper extends ElementScraper {
    public TableScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            ListNode<ListNode<?>> table = scrapTable(element);
            //gets image and description if its only content
            if (element.select("img").size() == 1 && table.getContent().size() <= 2) {
                return getImageFromTable(table);
            }
            return table;
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }

    private ListNode<ListNode<?>> scrapTable(Element element) {
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


    private ListNode<ListNode<?>> scrapDefaultTable(Element element) {
        List<ListNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow : element.children()) {
            tableRows.add(scrapRow(tableRow));
        }
        return new ListNode<>(ContentNodeType.TABLE, tableRows);
    }


    private ContentNode<?> getImageFromTable(ListNode<ListNode<?>> table) {
        EmbedNode embedNode = null;
        ParagraphNode caption = null;
        for (ListNode<?> row : table.getContent()) {
            if (row instanceof ParagraphNode) continue;
            List<ContentNode<?>> cells = (List<ContentNode<?>>) row.getContent();
            for (ContentNode<?> cell : cells) {
                if (cell instanceof ListNode) {
                    ListNode<?> cellNode = (ListNode<?>) cell;
                    EmbedNode foundImage = (EmbedNode) cellNode.getContent().stream()
                            .filter(contentNode -> contentNode instanceof EmbedNode)
                            .findFirst().orElse(null);
                    ParagraphNode foundCaption = (ParagraphNode) cellNode.getContent().stream()
                            .filter(contentNode -> contentNode instanceof ParagraphNode)
                            .findFirst().orElse(null);
                    if (embedNode == null && foundImage != null) embedNode = foundImage;
                    if (caption == null && foundCaption != null) caption = foundCaption;
                }
            }
        }
        if (embedNode != null && caption != null && embedNode.getDescription().isEmpty()) {
            embedNode.setDescription(caption.getContent());
        }

        //if image is null, return table
        if (embedNode != null) return embedNode;
        else return table;

    }

    private ListNode<ListNode<?>> scrapEnBaseTable(Element element) {
        Element itemHeaders = element.getElementsByClass("item1 EN").get(0);
        ListNode<ListNode<?>> paragraphs = new ListNode<>(ContentNodeType.PARAGRAPHS);
        for (Element itemElement : itemHeaders.children()) {
            ParagraphNode paragraph = new ParagraphNode();
            List<TextNode> textNodes;
            ///itemElement has one child element in most cases
            if (itemElement.children().size() == 1) {
                Element childElement = itemElement.child(0);
                if (childElement.children().isEmpty()) {
                    String text = itemElement.text();
                    //typical wiki stylization applying
                    if (text.contains(":")) {
                        String[] splitElements = itemElement.text().split(": ");
                        TextNode strongNode = new TextNode(splitElements[0].trim() + ": ");
                        strongNode.addStyle("font-weight", "bold");
                        strongNode.setContent(strongNode.getContent());
                        textNodes = List.of(strongNode, new TextNode(splitElements[1].trim()));
                    } else {
                        textNodes = List.of(new TextNode(childElement.text()));
                    }
                } else {
                    textNodes = TextScraper.scrap(childElement, source);
                }
            } else {
                textNodes = TextScraper.scrap(itemElement, source);
                textNodes.get(0).addStyle("font-weight", "bold");
            }

            //for one text node, append to last paragraph and prepend with space
            if (textNodes.size() == 1 && !paragraphs.isEmpty()) {
                TextNode textNode = textNodes.get(0);
                textNode.setContent(" "+textNode.getContent());
                ParagraphNode lastParagraph = (ParagraphNode) paragraphs.getLastElement();
                lastParagraph.addElements(textNodes);
            } else {
                paragraph.addElements(textNodes);
                paragraphs.addElement(paragraph);
            }
        }
        return paragraphs;
    }

    private ListNode<ListNode<?>> scrapResponsiveTable(Element table) {
        List<ListNode<?>> tableRows = new ArrayList<>();
        for (Element tableRow : table.children()) {
            tableRows.add(scrapResponsiveTableRow(tableRow));
        }
        return new ListNode<>(ContentNodeType.TABLE, tableRows);
    }

    private ListNode<?> scrapResponsiveTableRow(Element row) {
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

    private ListNode<?> scrapRow(Element row) {
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
