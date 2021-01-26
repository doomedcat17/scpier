package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentNode;
import pl.doomedcat17.scpapi.data.ContentNodeType;
import pl.doomedcat17.scpapi.data.Image;

public class ImageMapper extends HtmlMapper {
    @Override
    public Appendix mapElement(Element element) {
        Appendix appendix = new Appendix();
        ContentNode<Image> contentNode = new ContentNode<>(ContentNodeType.IMAGE);
        String imageSource = element
                .attributes().get("src");
        Image image = new Image();
        image.setSource(imageSource);
        contentNode.setContent(image);
        appendix.addContentNode(contentNode);
        return appendix;
    }
}
