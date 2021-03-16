package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.content_node.ImageNode;
import com.doomedcat17.scpier.data.content_node.VideoNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.TextScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.video.VideoScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.scrapper.htmlscrappers.image.ImageScrapper;

import java.util.ArrayList;
import java.util.List;

public class ImageBlockScrapper extends DivScrapper implements DivScrapperComponent {
    public ImageBlockScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Element imageElement = element.selectFirst("img");
        Appendix appendix = new Appendix();
        if (imageElement == null) {
            imageElement = element.selectFirst("video");
            VideoScrapper videoScrapper = new VideoScrapper(source, titleResolver);
            VideoNode videoNode = (VideoNode) videoScrapper.scrapElement(imageElement).getContents().get(0);
            videoNode.getCaption().addAll(getCaption(element));
            appendix.addContentNode(videoNode);
        } else {
            ImageScrapper imageMapper = new ImageScrapper(source, titleResolver);
            imageElement = element.selectFirst("img");
            ImageNode imageNode = (ImageNode) imageMapper.scrapElement(imageElement).getContents().get(0);
            imageNode.getCaption().addAll(getCaption(element));
            appendix.addContentNode(imageNode);
        }
        return new ArrayList<>(List.of(appendix));
    }
    private List<TextNode> getCaption(Element element) {
        Element captionElement = element.selectFirst(".scp-image-caption");
        List<TextNode> textNodes = new ArrayList<>();
        if (captionElement != null && !captionElement.childNodes().isEmpty()) {
            captionElement.childNodes().forEach(node -> {
                if (node instanceof Element) {
                    textNodes.addAll(TextScrapper.scrapText((Element) node, source));
                } else {
                    String nodeText = node.toString().trim();
                    if (!nodeText.isEmpty()) textNodes.add(new TextNode(nodeText));
                }
            });
        }
        return textNodes;
    }
}
