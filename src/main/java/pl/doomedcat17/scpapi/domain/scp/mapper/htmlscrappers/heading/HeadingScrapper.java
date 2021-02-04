package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.heading;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.data.content_node.TextNode;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.HtmlScrapper;

import java.util.List;

public class HeadingScrapper extends HtmlScrapper {
    @Override
    //TODO refactor, does not apply element style
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<TextNode>> headingContentNode = new ContentNode<>(ContentNodeType.HEADING);
        List<TextNode> textNodes = textScrapper.scrapText(element);
        headingContentNode.setContent(textNodes);
        if (element.parent().is("#page-content")) {
            appendix.setTitle(titleFinder.margeHeading(textNodes));
        } else appendix.addContentNode(headingContentNode);
        return appendix;
    }
}
