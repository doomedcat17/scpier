package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.div_scrapper_componetnts;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import pl.doomedcat17.scpapi.data.content_node.ContentNode;
import pl.doomedcat17.scpapi.data.content_node.Image;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.image.ImageScrapper;

import java.util.ArrayList;
import java.util.List;

public class ImageBlockScrapper extends DivScrapperComponent {
    @Override
    public List<Appendix> scrapDivContent(Element element) {
        ImageScrapper imageMapper = new ImageScrapper();
        Appendix appendix = imageMapper.scrapElement(element.selectFirst("img"));
        ContentNode<Image> contentNode = (ContentNode<Image>) appendix.getContents().get(0);
        Element imageCaptionElement = element.selectFirst(".scp-image-caption");
        if (imageCaptionElement != null) {
            String imageCaption = imageCaptionElement.text().trim();
            contentNode.getContent().setCaption(imageCaption);
        }
        return new ArrayList<>(List.of(appendix));
    }
}
