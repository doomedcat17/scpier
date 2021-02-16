package mapper.htmlscrappers.list;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;
import mapper.htmlscrappers.HtmlScrapper;

import java.util.ArrayList;
import java.util.List;

public class ListScrapper extends HtmlScrapper {
    @Override
    public Appendix scrapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        if (element.tagName().equals("ul")) {
            contentNode.setContentNodeType(ContentNodeType.LIST_UL);
        } else contentNode.setContentNodeType(ContentNodeType.LIST_OL);
        contentNode.setContent(new ArrayList<>());
        mapList(element, contentNode);
        appendix.addContentNode(contentNode);
        return appendix;
    }

    private void mapList(Element element, ContentNode<List<ContentNode<?>>> contentNode) {
        Elements children = element.children();
        children.forEach(
                child -> contentNode.getContent().add(mapRow(child))
        );
    }

    private ContentNode<?> mapRow(Element row) {
        if (row.is("ul, ol")) {
            Appendix nestedAppendix = scrapElement(row);
            return nestedAppendix.getContents().get(0);

        } else {
            List<ContentNode<?>> contentNodes = scrapElementContent(row); // wcześniej  margeTextNodes(mapElementContent(row))
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else {
                return new ContentNode<>(ContentNodeType.ELEMENTS, contentNodes);
            }
        }
    }

}
