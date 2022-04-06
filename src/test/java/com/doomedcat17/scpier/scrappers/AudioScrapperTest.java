package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.ContentNodeType;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.scraper.audio.AudioScraper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioScrapperTest extends ScrapperTest {

    private final AudioScraper audioScrapper = new AudioScraper(SOURCE);

    private final Element sampleElements = TestDataProvider
            .getSampleElement("src/test/resources/html/test_data/audio/sample-audio-elements.html");

    @Test
    void shouldScrapAudioElement()  {
        //given
        Element element = sampleElements.getElementById("shouldScrapAudioElement");
        //when
        ContentNode<?> contentNode = audioScrapper.scrapElement(element);
        //then
        assertTrue(contentNode instanceof EmbedNode && contentNode.getContentNodeType().equals(ContentNodeType.AUDIO));
        assertEquals("http://www.scp-wiki.net/local--files/scp-049/Addendum0492.mp3", contentNode.getContent());
    }

    @Test
    void shouldScrapAudioPlayerElement()  {
        //given
        Element element = sampleElements.getElementById("shouldScrapAudioPlayerElement");
        //when
        ContentNode<?> contentNode = audioScrapper.scrapElement(element);
        //then
        assertTrue(contentNode instanceof EmbedNode && contentNode.getContentNodeType().equals(ContentNodeType.AUDIO));
        assertEquals("http://www.scp-wiki.net/local--files/scp-049/Addendum0492.mp3", contentNode.getContent());
    }

}