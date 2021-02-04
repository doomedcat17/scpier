package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers;

import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;
import pl.doomedcat17.scpapi.data.scp.ScpPattern;

import java.util.Iterator;
import java.util.List;

public class TitleFinder {

    private final int MIN_HEADER_LENGTH = 21;

    private final int MAX_HEADER_LENGTH = 69;

    private final int TITLE_DEPTH = 2; //how deep inside divs/blockquotes look for title


    public boolean isTittle(String title) {
        if (ScpPattern.containsValue(title, "eng")) {
            return true;
        } else {
            return (title.length() >= MIN_HEADER_LENGTH && title.length() <= MAX_HEADER_LENGTH);
        }
    }

    public String lookForTittle(List<ContentNode<?>> contentNodes) {
        String title = "";
        Iterator<ContentNode<?>> contentNodeIterator = contentNodes.iterator();
        int depthCounter = 0;
        while (contentNodeIterator.hasNext()) {
            if (depthCounter == TITLE_DEPTH) break;
            ContentNode<?> contentNode = contentNodeIterator.next();
            if (contentNode.getContentNodeType().equals(ContentNodeType.HEADING)) {
                ContentNode<List<TextNode>> headingNode = (ContentNode<List<TextNode>>) contentNode;
                String headingText = margeHeading(headingNode.getContent());
                if (isTittle(headingText)) {
                    title = headingText;
                    contentNodes.remove(headingNode);
                    break;
                }
            }
            depthCounter++;
        }
        return title;
    }


    public String margeHeading(List<TextNode> textNodes) {
        StringBuilder stringBuilder = new StringBuilder();
        textNodes.forEach(textNode -> stringBuilder.append(textNode.getContent()));
        return stringBuilder.toString();
    }
}
