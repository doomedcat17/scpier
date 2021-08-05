package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.audio.AudioScraper;
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

    //usually it contains image, but there are some special cases
    //TODO tests
    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        List<ContentNode<?>> contentNodes = new ArrayList<>();
        Element imageElement = element.selectFirst("img");
        EmbedNode embedNode = new EmbedNode();
        if (imageElement == null) {
            if (element.selectFirst("video") != null) embedNode = scrapVideo(element, source);
            else if (element.selectFirst("audio") != null) embedNode = scrapAudio(element, source);
        } else embedNode = scrapImage(element, source);
        if (!embedNode.isEmpty()) {
            contentNodes.add(embedNode);
            embedNode.getDescription().addAll(getCaption(element));
        }
        return contentNodes;
    }

    private EmbedNode scrapImage(Element element, String source) {
        ImageScraper imageMapper = new ImageScraper(source);
        Element imgElement = element.selectFirst("img");
        return (EmbedNode) imageMapper.scrapElement(imgElement);
    }

    private EmbedNode scrapVideo(Element element, String source) {
        Element videoElement = element.selectFirst("video");
        VideoScraper videoScrapper = new VideoScraper(source);
        return  (EmbedNode) videoScrapper.scrapElement(videoElement);
    }

    private EmbedNode scrapAudio(Element element, String source) {
        Element videoElement = element.selectFirst("audio");
        AudioScraper audioScrapper = new AudioScraper(source);
        return  (EmbedNode) audioScrapper.scrapElement(videoElement);
    }

    //scraps image/video caption
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
