package com.doomedcat17.scpier.scrapper.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ImageNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.data.contentnode.VideoNode;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.image.ImageScrapper;
import com.doomedcat17.scpier.scrapper.text.TextScrapper;
import com.doomedcat17.scpier.scrapper.video.VideoScrapper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class ImageBlockScrapper extends DivScrapper implements DivScrapperComponent {
    public ImageBlockScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        Element imageElement = element.selectFirst("img");
        if (imageElement == null) {
            imageElement = element.selectFirst("video");
            VideoScrapper videoScrapper = new VideoScrapper(source);
            VideoNode videoNode = (VideoNode) videoScrapper.scrapElement(imageElement);
            videoNode.getCaption().addAll(getCaption(element));
            contentNodes.add(videoNode);
        } else {
            ImageScrapper imageMapper = new ImageScrapper(source);
            imageElement = element.selectFirst("img");
            ImageNode imageNode = (ImageNode) imageMapper.scrapElement(imageElement);
            imageNode.getCaption().addAll(getCaption(element));
            contentNodes.add(imageNode);
        }
        return contentNodes;
    }
    private List<TextNode> getCaption(Element element)  {
        Element captionElement = element.selectFirst(".scp-image-caption");
        List<TextNode> textNodes = new ArrayList<>();
        if (captionElement != null && !captionElement.childNodes().isEmpty()) {
            for(Node node: captionElement.childNodes()) {
                if (node instanceof Element) {
                    textNodes.addAll(TextScrapper.scrapText((Element) node, source));
                } else {
                    String nodeText = node.toString().trim();
                    if (!nodeText.isEmpty()) textNodes.add(new TextNode(nodeText));
                }
            }
        }
        return textNodes;
    }
}