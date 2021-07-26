package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.image.ImageScraper;
import com.doomedcat17.scpier.scraper.text.TextScraper;
import com.doomedcat17.scpier.scraper.video.VideoScraper;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class ImageBlockScraper extends DivScraper implements DivScraperComponent {
    public ImageBlockScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        Element imageElement = element.selectFirst("img");
        if (imageElement == null) {
            imageElement = element.selectFirst("video");
            VideoScraper videoScrapper = new VideoScraper(source);
            EmbedNode videoNode = (EmbedNode) videoScrapper.scrapElement(imageElement);
            videoNode.getDescription().addAll(getCaption(element));
            contentNodes.add(videoNode);
        } else {
            ImageScraper imageMapper = new ImageScraper(source);
            imageElement = element.selectFirst("img");
            EmbedNode embedNode = (EmbedNode) imageMapper.scrapElement(imageElement);
            embedNode.getDescription().addAll(getCaption(element));
            contentNodes.add(embedNode);
        }
        return contentNodes;
    }
    private List<TextNode> getCaption(Element element)  {
        Element captionElement = element.selectFirst(".scp-image-caption");
        List<TextNode> textNodes = new ArrayList<>();
        if (captionElement != null && !captionElement.childNodes().isEmpty()) {
            for(Node node: captionElement.childNodes()) {
                if (node instanceof Element) {
                    textNodes.addAll(TextScraper.scrapText((Element) node, source));
                } else {
                    String nodeText = node.toString().trim();
                    if (!nodeText.isEmpty()) textNodes.add(new TextNode(nodeText));
                }
            }
        }
        return textNodes;
    }
}
