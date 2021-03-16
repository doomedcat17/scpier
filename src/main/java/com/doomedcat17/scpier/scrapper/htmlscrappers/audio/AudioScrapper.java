package com.doomedcat17.scpier.scrapper.htmlscrappers.audio;

import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.AudioNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ElementScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import org.jsoup.nodes.Element;

public class AudioScrapper extends ElementScrapper {
    public AudioScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public Appendix scrapElement(Element element) {
        String source = "";
        if (element.is("audio-player")) {
            source = element.attr("file");
        } else {
            Element sourceElement = element.selectFirst("source");
            if (sourceElement != null) {
                source = sourceElement.attr("src");
            } else source = element.attr("src");
        }
        AudioNode audioNode = new AudioNode(source);
        Appendix appendix = new Appendix();
        appendix.addContentNode(audioNode);
        return appendix;
    }
}
