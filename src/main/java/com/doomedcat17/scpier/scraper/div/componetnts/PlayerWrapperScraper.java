package com.doomedcat17.scpier.scraper.div.componetnts;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.audio.AudioScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class PlayerWrapperScraper extends DivScraper implements DivScraperComponent {
    public PlayerWrapperScraper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element)  {
        if (element.is(".widget")) {
            Element audioAnchor = element.selectFirst(".sample_info > a");
            EmbedNode audioNode = new EmbedNode();
            audioNode.setContentNodeType(ContentNodeType.AUDIO);
            audioNode.setContent(audioAnchor.attr("href"));
            audioNode.getDescription().add(new TextNode(audioAnchor.text()));
            return new ArrayList<>(List.of(audioNode));
        }
        Element audioElement = element.selectFirst("audio, audio-player");
        AudioScraper audioScrapper = new AudioScraper(source);
        return new ArrayList<>(List.of(audioScrapper.scrapElement(audioElement)));
    }
}
