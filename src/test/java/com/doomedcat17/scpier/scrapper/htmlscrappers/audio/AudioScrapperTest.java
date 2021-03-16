package com.doomedcat17.scpier.scrapper.htmlscrappers.audio;

import com.doomedcat17.scpapi.TestDataProvider;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.AudioNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ScrapperTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AudioScrapperTest extends ScrapperTest {

    private final AudioScrapper audioScrapper = new AudioScrapper(SOURCE, titleResolver);

    private final Element sampleElements = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/audio/sampleAudioElements.html");

    @Test
    void shouldScrapAudioElement() {
        //given
        Element element = sampleElements.getElementById("shouldScrapAudioElement");
        //when
        Appendix appendix = audioScrapper.scrapElement(element);
        //then
        assertTrue(appendix.getContents().get(0) instanceof AudioNode);
        assertEquals("http://www.scp-wiki.net/local--files/scp-049/Addendum0492.mp3", appendix.getContents().get(0).getContent());
    }

    @Test
    void shouldScrapAudioPlayerElement() {
        //given
        Element element = sampleElements.getElementById("shouldScrapAudioPlayerElement");
        //when
        Appendix appendix = audioScrapper.scrapElement(element);
        //then
        assertTrue(appendix.getContents().get(0) instanceof AudioNode);
        assertEquals("http://www.scp-wiki.net/local--files/scp-049/Addendum0492.mp3", appendix.getContents().get(0).getContent());
    }

}