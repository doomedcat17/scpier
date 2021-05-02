package com.doomedcat17.scpier.scrapper.line;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.HyperlinkNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
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
        if (element.is("br")) {
            return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        }
        lineCleanup(element);
        //in few cases there are only empty chars and
        if (!element.wholeText().isBlank()) {
            if (element.parent() != null) {
                if (element.is("a") && !element.hasClass("footnoteref")
                        && element.hasAttr("href")
                        && !element.attr("href").equals("#")) {
                    ContentNode<List<HyperlinkNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                    String href = element.attr("href");
                    if (href.startsWith("/")) {
                        href = source.substring(0, source.lastIndexOf('/')) + href;
                    }
                    HyperlinkNode hyperlinkNode = new HyperlinkNode(element.text(), href);
                    paragraph.getContent().add(hyperlinkNode);
                    return paragraph;
                } else {
                    List<TextNode> textNodes = TextScrapper.scrapText(element, source);
                    if (textNodes.isEmpty()) return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                    textNodes.get(textNodes.size() - 1).setContent(textNodes.get(textNodes.size() - 1).getContent().stripTrailing());
                    ContentNode<List<ContentNode<List<TextNode>>>> paragraphs = new ContentNode<>(ContentNodeType.PARAGRAPHS);
                    paragraphs.setContent(splitIntoParagraphs(textNodes));
                    if (paragraphs.getContent().size() == 1) return paragraphs.getContent().get(0);
                    return paragraphs;
                }
            }
        }
        return new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
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
    public List<ContentNode<List<TextNode>>> splitIntoParagraphs(List<TextNode> textNodes) {
        List<ContentNode<List<TextNode>>> paragraphs = new ArrayList<>();
        Iterator<TextNode> textNodeIterator = textNodes.iterator();
        ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        while (textNodeIterator.hasNext()) {
            TextNode textNode = textNodeIterator.next();
            textNode.setContent(textNode.getContent().replace("\r", ""));
            if (textNode.getContent().isEmpty()) continue;
            if (textNode.getContent().equals("\n")) {
                if (paragraph.getContent().isEmpty()) paragraph.getContent().add(new TextNode(""));
                paragraphs.add(paragraph);
                paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            } else {
                List<Integer> lineIndexes = findAllNewLineChars(textNode.getContent());
                int startIndex = 0;
                if (!lineIndexes.isEmpty()) {
                    for (Integer index : lineIndexes) {
                        String text = textNode.getContent().substring(startIndex, index);
                        startIndex = index + 1;
                        if (text.isEmpty()) continue;
                        paragraph.getContent().add(new TextNode(text, textNode.getStyles()));
                        paragraphs.add(paragraph);
                        paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                    }
                    String tailingText = textNode.getContent().substring(startIndex);
                    if (!tailingText.isEmpty()) {
                        paragraphs.add(new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(new TextNode(tailingText, textNode.getStyles())))));
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
