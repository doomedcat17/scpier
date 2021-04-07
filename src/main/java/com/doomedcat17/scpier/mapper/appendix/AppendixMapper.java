package com.doomedcat17.scpier.mapper.appendix;

import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleFinder;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;

import java.util.ArrayList;
import java.util.List;

public class AppendixMapper {
    public static List<Appendix> mapContentNodesToAppendices(List<ContentNode<?>> contentNodes, TitleResolver titleResolver) {
        List<Appendix> appendices = new ArrayList<>();
        for (ContentNode<?> contentNode : contentNodes) {
            if (contentNode.isEmpty()) continue;
            ContentNodeType contentNodeType = contentNode.getContentNodeType();
            if (contentNodeType.equals(ContentNodeType.PARAGRAPH) || contentNodeType.equals(ContentNodeType.DIV) || contentNodeType.equals(ContentNodeType.BLOCKQUOTE)) {
                List<ContentNode<?>> nodeContent = (List<ContentNode<?>>) contentNode.getContent();
                String title = TitleFinder.lookForTitle(nodeContent, titleResolver);
                if (!title.isBlank()) {
                    if (contentNodeType.equals(ContentNodeType.PARAGRAPH)) appendices.add(new Appendix(title, new ArrayList<>(List.of(contentNode))));
                    else appendices.add(new Appendix(title, nodeContent));
                }
            } else if (contentNodeType.equals(ContentNodeType.HEADING)) {
                String headingText = TextScrapper.margeTextNodes((List<TextNode>) contentNode.getContent());
                if (titleResolver.isTitle(headingText)) appendices.add(new Appendix(headingText, new ArrayList<>()));
            } else {
                if (appendices.isEmpty()) {
                    appendices.add(new Appendix("HEADING", new ArrayList<>(List.of(contentNode))));
                }
            }
        }
        return appendices;
    }
}
