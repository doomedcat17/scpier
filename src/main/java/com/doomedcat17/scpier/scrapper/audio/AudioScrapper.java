package com.doomedcat17.scpier.scrapper.audio;

import com.doomedcat17.scpier.data.contentnode.AudioNode;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import org.jsoup.nodes.Element;

public class AudioScrapper extends ElementScrapper {
    public AudioScrapper(String source) {
        super(source);
    }

    @Override
    public ContentNode<?> scrapElement(Element element) {
        String source = "";
        if (element.is("audio-player")) {
            source = element.attr("file");
        } else {
            Element sourceElement = element.selectFirst("source");
            if (sourceElement != null) {
                source = sourceElement.attr("src");
            } else source = element.attr("src");
        }
        return new AudioNode(source);
    }
}
