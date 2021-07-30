package com.doomedcat17.scpier.scraper.line;

import com.doomedcat17.scpier.data.content.*;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.List;

public class LineScraper extends ElementScraper {

    private final TextNodeSplitter textNodeSplitter = new TextNodeSplitter();

    public LineScraper(String source) {
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
                        List<TextNode> textNodes = TextScraper.scrapText(element, source);
                        if (textNodes.isEmpty()) return new ParagraphNode();
                        if (element.is("a")) return textNodes.get(0);
                        textNodes.get(textNodes.size() - 1).setContent(textNodes.get(textNodes.size() - 1).getContent().stripTrailing());
                        ListNode<ParagraphNode> paragraphs = new ListNode<>(ContentNodeType.PARAGRAPHS);
                        paragraphs.setContent(textNodeSplitter.splitIntoParagraphs(textNodes));
                        if (paragraphs.getContent().size() == 1) return paragraphs.getContent().get(0);
                        return paragraphs;
                }
            }
            return new ParagraphNode();
        } catch (Exception e) {
            throw new ElementScraperException(e);
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
                firstNodeText = e.wholeText();
            } else firstNodeText = node.toString();
            //removing first node if empty or has only Unicode Byte Order Mark
            if (firstNodeText.length() == 0 || ((firstNodeText.charAt(0) == 65279 || firstNodeText.charAt(0) == 8203) && firstNodeText.length() == 1)) {
                element.childNodes().get(0).remove();
            }
        }
    }
}
