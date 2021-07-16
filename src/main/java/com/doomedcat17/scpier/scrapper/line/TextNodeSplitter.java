package com.doomedcat17.scpier.scrapper.line;

import com.doomedcat17.scpier.data.contentnode.HyperlinkNode;
import com.doomedcat17.scpier.data.contentnode.ParagraphNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextNodeSplitter {

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
                        } else {
                            paragraph.addElement(new TextNode(text, textNode.getStyles()));
                        }
                        paragraphs.add(paragraph);
                        paragraph = new ParagraphNode();
                    }
                    String tailingText = textNode.getContent().substring(startIndex);
                    if (!tailingText.isEmpty()) {
                        paragraphs.add(new ParagraphNode(new ArrayList<>(List.of(new TextNode(tailingText, textNode.getStyles())))));
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
