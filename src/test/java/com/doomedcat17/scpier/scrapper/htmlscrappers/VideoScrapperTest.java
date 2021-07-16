package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.EmbedNode;
import com.doomedcat17.scpier.scrapper.video.VideoScrapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VideoScrapperTest extends ScrapperTest {

    private final VideoScrapper videoScrapper = new VideoScrapper(SOURCE);

    private final Element sampleElements = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/video/sampleVideoElements.html");

    @Test
    void shouldScrapVideo()  {
        //given
        Element videoElement = sampleElements.getElementById("shouldScrapVideo");
        //when
        ContentNode<?> contentNode = videoScrapper.scrapElement(videoElement);
        //then
        assertTrue(contentNode instanceof EmbedNode);
        assertEquals("http://kontainer.djkakt.us/local--files/popsioak-6/vid", contentNode.getContent());
    }

}