package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.List;

public class BlockquoteMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> blockquoteNode = new ContentNode<>(ContentNodeType.BLOCKQUOTE);
        List<ContentNode<?>> contentNodes = mapElementContent(element);
        if (!appendix.hasTitle()) {
            appendix.setTitle(lookForTittle(contentNodes));
        }
        blockquoteNode.setContent(contentNodes);
        appendix.addContentNode(blockquoteNode);
       return appendix;
    }
}
