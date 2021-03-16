package com.doomedcat17.scpier.scp.scp_mappers.appendix_mappers;

import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.ContentNodeType;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.exceptions.MapperNotFoundException;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.HtmlScrapperFactory;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleFinder;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class AppendixMapper {

    public static Appendix mapElementToAppendix(Element element, String source, TitleResolver titleResolver) throws MapperNotFoundException {
        ElementScrapper elementScrapper = HtmlScrapperFactory.getHtmlScrapper(element, source, titleResolver);
        Appendix appendix = elementScrapper.scrapElement(element);
        if (!appendix.hasTitle() && !appendix.getContents().isEmpty()) {
            String foundTitle =
                    TitleFinder.lookForTitle(appendix.getContents(), titleResolver);
            if (!foundTitle.isEmpty()) {
                appendix.setTitle(foundTitle);
            }
        }
        return appendix;
    }

    public static List<Appendix> mapNodesToAppendices(List<Node> nodes, String source, TitleResolver titleResolver) {
        List<Appendix> appendices = new ArrayList<>();
        for (Node node : nodes) {
            if (node.toString().isBlank()) continue;
            if (node instanceof Element) {
                Element element = (Element) node;
                try {
                    Appendix appendix = mapElementToAppendix(element, source, titleResolver);
                        if (!appendix.hasTitle()) {
                            if (!appendix.getContents().isEmpty()) {
                                resolveAppendixWithoutTitle(appendix, appendices);
                            }
                        } else appendices.add(appendix);
                } catch (MapperNotFoundException e) {
                }
            } else {
                if (!appendices.isEmpty()) mapNode(node, appendices);
            }
        }
        return appendices;
    }

    private static void resolveAppendixWithoutTitle(Appendix appendix, List<Appendix> appendices) {
        if (appendix.getContents().size() == 1 && appendix.getContents().get(0).getContentNodeType().equals(ContentNodeType.APPENDICES)) {
            ContentNode<List<Appendix>> contentNode = (ContentNode<List<Appendix>>) appendix.getContents().get(0);
            appendices.addAll(contentNode.getContent());
        } else {
            if (!appendices.isEmpty()) {
                addContentNodesToLastAppendix(appendices, appendix);
            } else {
                appendices.add(appendix);
            }
        }
    }

    private static void addContentNodesToLastAppendix(List<Appendix> appendices, Appendix appendix) {
        for (ContentNode<?> contentNode : appendix.getContents()) {
            if (contentNode.getContent() != null) {
                if (!contentNode.getContent().toString().isBlank()) {
                    appendices.get(appendices.size() - 1).addContentNode(contentNode);
                }
            }
        }
    }

    private static void mapNode(Node node, List<Appendix> appendices) {
        if (appendices.get(appendices.size() - 1).getContents().isEmpty()) {
            ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
            paragraph.getContent().add(new TextNode(node.toString().trim()));
            appendices.get(appendices.size() - 1).addContentNode(paragraph);
        } else {
            ContentNode<?> lastContentNode = appendices.get(appendices.size() - 1).getLastContentNode();
            if (lastContentNode instanceof TextNode) {
                TextNode lastTextNode = (TextNode) lastContentNode;
                lastTextNode.setContent(lastContentNode.getContent() + node.toString());
            } else if (lastContentNode.getContentNodeType().equals(ContentNodeType.PARAGRAPH)) {
                ContentNode<List<ContentNode<?>>> lastParagraph = (ContentNode<List<ContentNode<?>>>) lastContentNode;
                lastParagraph.getContent().add(new TextNode(node.toString()));
            } else appendices.get(appendices.size() - 1).addContentNode(new ContentNode<List<TextNode>>(ContentNodeType.PARAGRAPH, new ArrayList<>(List.of(new TextNode(node.toString())))));
        }
    }
}
