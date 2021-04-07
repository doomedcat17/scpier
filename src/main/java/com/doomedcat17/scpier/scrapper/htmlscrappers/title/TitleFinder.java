package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import org.jsoup.internal.StringUtil;

import java.util.Iterator;
import java.util.List;

public class TitleFinder {

    //this class looks for title inside list of nodes
    //if title was found, proper contentNode is deleted

    private static final int TITLE_DEPTH = 2; //how deep inside look for title

    public static String lookForTitle(List<ContentNode<?>> contentNodes, TitleResolver titleResolver) {
        String title = "";
        Iterator<ContentNode<?>> contentNodeIterator = contentNodes.iterator();
        int depthCounter = 0;
        while (contentNodeIterator.hasNext()) {
            if (depthCounter == TITLE_DEPTH) break;
            ContentNode<?> contentNode = contentNodeIterator.next();
            //look inside div, paragraph or blockquote for title
            if (contentNode.getContentNodeType().equals(ContentNodeType.DIV) ||
                    contentNode.getContentNodeType().equals(ContentNodeType.PARAGRAPH) ||
                    contentNode.getContentNodeType().equals(ContentNodeType.BLOCKQUOTE)) {
                List<ContentNode<?>> innerContentNodes = (List<ContentNode<?>>) contentNode.getContent();
                title = lookForTitle(innerContentNodes, titleResolver);
                //remove contentNode if is empty after finding a title
                if (!title.isEmpty() && innerContentNodes.isEmpty()) contentNodeIterator.remove();
                break;
            } else if (contentNode.getContentNodeType().equals(ContentNodeType.HEADING)) {

                String headingText = TextScrapper.margeTextNodes((List<TextNode>) contentNode.getContent());
                if (titleResolver.isTitle(headingText)) {
                    title = headingText;
                    contentNodeIterator.remove();
                    break;
                }
            } else if (contentNode instanceof TextNode) {
                TextNode textNode = (TextNode) contentNode;
                //bold TextNodes are considered as title
                if (isStyledStrong(textNode)) {
                    if (titleResolver.isTitle(textNode.getContent())) {
                        title = textNode.getContent().trim();
                        contentNodeIterator.remove();
                        break;
                    }
                }
            }
            depthCounter++;
        }
        //if there is next TextNode, strip its leading whitespace characters
        if (!title.isEmpty() && contentNodeIterator.hasNext()) {
            ContentNode<?> nextNode = contentNodeIterator.next();
            if (nextNode instanceof TextNode) {
                TextNode textNode = (TextNode) nextNode;
                textNode.setContent(textNode.getContent().stripLeading());
            }
        }
        //remove colon form the ending of title
        if (!title.isEmpty() && title.charAt(title.length() - 1) == ':') title = title.substring(0, title.length() - 1);
        return title;
    }

    private static boolean isStyledStrong(TextNode textNode) {
        if (textNode.getStyles().containsKey("font-weight")) {
            String fontWeightValue = textNode.getStyles().get("font-weight");
            if (StringUtil.isNumeric(fontWeightValue)) {
                int value = Integer.parseInt(fontWeightValue);
                return value >= 700;
            } else return fontWeightValue.equals("bold");
        } else return false;
    }
}
