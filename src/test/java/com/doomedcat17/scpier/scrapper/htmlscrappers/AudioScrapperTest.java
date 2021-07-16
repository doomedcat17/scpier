package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.EmbedNode;
import com.doomedcat17.scpier.scrapper.audio.AudioScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AudioScrapperTest extends ScrapperTest {

    private final AudioScrapper audioScrapper = new AudioScrapper(SOURCE);

    private final Element sampleElements = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/audio/sampleAudioElements.html");

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