package com.doomedcat17.scpier.scrapper.line;

import com.doomedcat17.scpier.data.contentnode.*;
import com.doomedcat17.scpier.exception.ElementScrapperException;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LineScrapper extends ElementScrapper {
    public LineScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        try {
            if (element.is("br")) {
                return new ParagraphNode();
            }
            lineCleanup(element);
            //in few cases there are only empty chars and
            if (!element.wholeText().isBlank()) {
                if (element.parent() != null) {
                    if (element.is("a") && !element.hasClass("footnoteref")
                            && element.hasAttr("href")
                            && !element.attr("href").equals("#")) {
                        ParagraphNode paragraph = new ParagraphNode();
                        String href = element.attr("href");
                        if (href.startsWith("/")) {
                            href = source.substring(0, source.lastIndexOf('/')) + href;
                        }
                        HyperlinkNode hyperlinkNode = new HyperlinkNode(element.text(), href);
                        paragraph.addElement(hyperlinkNode);
                        return paragraph;
                    } else {
                        List<TextNode> textNodes = TextScrapper.scrapText(element, source);
                        if (textNodes.isEmpty()) return new ParagraphNode();
                        textNodes.get(textNodes.size() - 1).setContent(textNodes.get(textNodes.size() - 1).getContent().stripTrailing());
                        ListNode<ParagraphNode> paragraphs = new ListNode<>(ContentNodeType.PARAGRAPHS);
                        paragraphs.setContent(splitIntoParagraphs(textNodes));
                        if (paragraphs.getContent().size() == 1) return paragraphs.getContent().get(0);
                        return paragraphs;
                    }
                }
            }
            return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        } catch (Exception e) {
            throw new ElementScrapperException(e.getMessage());
        }
    }

    //in some cases, first node of the paragraph is empty, this method takes care about it
    private void lineCleanup(Element element) {
        if (!element.childNodes().isEmpty()) {
            Node node = element.childNodes().get(0);
            String firstNodeText;
            if (node instanceof Element) {
                //in some cases node.toString() throws NullPointerException for Element
                Element e = (Element) node;
                firstNodeText = e.text();
            } else firstNodeText = node.toString();
            //removing first node if empty or has only Unicode Byte Order Mark
            if (firstNodeText.length() == 0 || (firstNodeText.charAt(0) == 65279 && firstNodeText.length() == 1)) {
                element.childNodes().get(0).remove();
            }
        }
    }

    //splits TextNodes into separate paragraphs basing on new line characters
    public List<ParagraphNode> splitIntoParagraphs(List<TextNode> textNodes) {
        List<ParagraphNode> paragraphs = new ArrayList<>();
        Iterator<TextNode> textNodeIterator = textNodes.iterator();
        ParagraphNode paragraph = new ParagraphNode();
        while (textNodeIterator.hasNext()) {
            TextNode textNode = textNodeIterator.next();
            textNode.setContent(textNode.getContent().replace("\r", ""));
            if (textNode.getContent().isEmpty()) continue;
            if (textNode.getContent().equals("\n")) {
                if (paragraph.isEmpty()) paragraph.addElement(new TextNode(""));
                paragraphs.add(paragraph);
                paragraph = new ParagraphNode();
            } else {
                List<Integer> lineIndexes = findAllNewLineChars(textNode.getContent());
                int startIndex = 0;
                if (!lineIndexes.isEmpty()) {
                    for (Integer index : lineIndexes) {
                        String text = textNode.getContent().substring(startIndex, index);
                        startIndex = index + 1;
                        if (text.isEmpty()) continue;
                        if (textNode instanceof HyperlinkNode) {
                            paragraph.addElement(new HyperlinkNode(text, textNode.getStyles(), ((HyperlinkNode) textNode).getHref()));
                            paragraph.addElement(textNode);
                            paragraphs.add(paragraph);
                            paragraph = new ParagraphNode();
                        }
                        String tailingText = textNode.getContent().substring(startIndex);
                        if (!tailingText.isEmpty()) {
                            paragraphs.add(new ParagraphNode(new ArrayList<>(List.of(new TextNode(tailingText, textNode.getStyles())))));
                        }
                    }

                } else paragraph.getContent().add(textNode);

            }

        }
        if (!paragraph.getContent().isEmpty()) paragraphs.add(paragraph);
        return paragraphs;
    }


    private List<Integer> findAllNewLineChars(String text) {
        List<Integer> indexes = new ArrayList<>();
        int index = text.indexOf('\n');
        while (index >= 0) {
            indexes.add(index);
            index = text.indexOf('\n', index + 1);
        }
        return indexes;
    }

}
