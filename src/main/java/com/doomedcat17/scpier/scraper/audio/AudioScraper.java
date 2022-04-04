package com.doomedcat17.scpier.scraper.audio;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.exception.scraper.ElementScraperException;
import com.doomedcat17.scpier.scraper.ElementScraper;
import org.jsoup.nodes.Element;

import java.util.Objects;

public class AudioScraper extends ElementScraper {
    public AudioScraper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element)  {
        try {
            String source = "";
            if (element.is("audio-player")) {
                source = element.attr("file");
            } else {
                Element sourceElement = element.selectFirst("source");
                source = Objects.requireNonNullElse(sourceElement, element).attr("src");
            }
            return new EmbedNode(ContentNodeType.AUDIO, source);
        } catch (Exception e) {
            throw new ElementScraperException(e);
        }
    }
}
