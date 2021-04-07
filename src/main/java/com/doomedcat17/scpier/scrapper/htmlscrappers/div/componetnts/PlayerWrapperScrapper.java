package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.audio.AudioScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class PlayerWrapperScrapper extends DivScrapper implements DivScrapperComponent{
    public PlayerWrapperScrapper(String source) {
        super(source);
    }

    @Override
    public List<ContentNode<?>> scrapDivContent(Element element) {
        Element audioElement = element.selectFirst("audio, audio-player");
        AudioScrapper audioScrapper = new AudioScrapper(source);
        return new ArrayList<>(List.of(audioScrapper.scrapElement(audioElement)));
    }
}
