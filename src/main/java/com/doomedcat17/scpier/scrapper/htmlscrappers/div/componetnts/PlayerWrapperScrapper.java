package com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts;

import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.scrapper.htmlscrappers.audio.AudioScrapper;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class PlayerWrapperScrapper extends DivScrapper implements DivScrapperComponent{
    public PlayerWrapperScrapper(String source, TitleResolver titleResolver) {
        super(source, titleResolver);
    }

    @Override
    public List<Appendix> scrapDivContent(Element element) {
        Element audioElement = element.selectFirst("audio, audio-player");
        AudioScrapper audioScrapper = new AudioScrapper(source, titleResolver);
        return new ArrayList<>(List.of(audioScrapper.scrapElement(audioElement)));
    }
}
