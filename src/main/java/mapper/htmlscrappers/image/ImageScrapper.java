package mapper.htmlscrappers.image;

import org.jsoup.nodes.Element;
import data.appendencies.Appendix;
import data.content_node.ContentNode;
import data.content_node.ContentNodeType;
import data.content_node.Image;
import mapper.htmlscrappers.HtmlScrapper;

public class ImageScrapper extends HtmlScrapper {
    @Override
    public Appendix scrapElement(Element element) {
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
