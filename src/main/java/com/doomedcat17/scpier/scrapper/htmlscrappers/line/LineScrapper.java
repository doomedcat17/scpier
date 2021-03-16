package com.doomedcat17.scpier.scrapper.htmlscrappers.line;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.HyperlinkNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LineScrapper extends ElementScrapper {
    public LineScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        if (element.is("br")) {
            appendix.addContentNode(new TextNode("\n"));
            return appendix;
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
                    appendix.addContentNode(paragraph);
                } else {
                    List<TextNode> textNodes = TextScrapper.scrapText(element, source);
                    textNodes.get(0).setContent(textNodes.get(0).getContent().stripLeading());
                    textNodes.get(textNodes.size() - 1).setContent(textNodes.get(textNodes.size() - 1).getContent().stripTrailing());
                    splitIntoParagraphs(textNodes).forEach(appendix::addContentNode);
                }
            }
        }
        return appendix;
    }

    //in some cases, first node of the paragraph is empty, this method takes care about it
    private void lineCleanup(Element element) {
        String firstNodeText = element.childNodes().get(0).toString();
        //removing first node if empty or has only Unicode Byte Order Mark
        if (firstNodeText.length() == 0 || (firstNodeText.charAt(0) == 65279 && firstNodeText.length() == 1)) {
            element.childNodes().get(0).remove();
        }
    }

    //splits TextNodes into separate paragraphs basing on new line characters
    private List<ContentNode<List<TextNode>>> splitIntoParagraphs(List<TextNode> textNodes) {
        List<ContentNode<List<TextNode>>> paragraphs = new ArrayList<>();
        Iterator<TextNode> textNodeIterator = textNodes.iterator();
        ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        while (textNodeIterator.hasNext()) {
            TextNode textNode = textNodeIterator.next();
            if (textNode.getContent().isEmpty()) continue;
            if (textNode.getContent().charAt(textNode.getContent().length() - 1) == '\n' || !textNodeIterator.hasNext()) {
                //strip trailing and add TextNode to paragraph if isn't blank
                if (!textNode.getContent().isBlank()) {
                    textNode.setContent(textNode.getContent().stripTrailing());
                    paragraph.getContent().add(textNode);
                }
                if (!paragraph.getContent().isEmpty()) {
                    paragraph.getContent().get(0).setContent(paragraph.getContent().get(0).getContent().stripLeading());
                    paragraphs.add(paragraph);
                    paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
                }
            } else paragraph.getContent().add(textNode);
        }
        if (!paragraph.getContent().isEmpty()) {
            TextNode lastTextNode = paragraph.getContent().get(paragraph.getContent().size() - 1);
            lastTextNode.setContent(lastTextNode.getContent().stripTrailing());
            paragraphs.add(paragraph);
        }
        return paragraphs;
    }
}
