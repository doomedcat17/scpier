package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.blockquote;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.ContentNodeType;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.HtmlScrapper;

import java.util.List;

public class BlockquoteScrapper extends HtmlScrapper {
    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> blockquoteNode = new ContentNode<>(ContentNodeType.BLOCKQUOTE);
        List<ContentNode<?>> contentNodes = scrapElementContent(element);
        if (!appendix.hasTitle()) {
            appendix.setTitle(titleFinder.lookForTittle(contentNodes));
        }
        blockquoteNode.setContent(contentNodes);
        appendix.addContentNode(blockquoteNode);
       return appendix;
    }
}
