package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;

import java.util.ArrayList;
import java.util.List;

//TODO tests needed
//TODO refactor code
public class ListMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<List<ContentNode<?>>> contentNode = new ContentNode<>();
        if (element.tagName().equals("ul")) {
            contentNode.setContentNodeType(ContentNodeType.LIST_UL);
        } else contentNode.setContentNodeType(ContentNodeType.LIST_OL);
        contentNode.setContent(new ArrayList<>());
        mapList(element, contentNode);
        appendix.addContentBox(contentNode);
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
            Appendix nestedAppendix = mapElement(row);
            return nestedAppendix.getContents().get(0);

        } else {
            List<ContentNode<?>> contentNodes = extractContent(row);
            if (contentNodes.size() == 1) {
                return contentNodes.get(0);
            } else {
                return new ContentNode<>(ContentNodeType.ELEMENTS, contentNodes);
            }
        }
    }

}
