package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.EmbedNode;
import com.doomedcat17.scpier.scraper.video.VideoScraper;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VideoScrapperTest extends ScrapperTest {

    private final VideoScraper videoScrapper = new VideoScraper(SOURCE);

    private final Element sampleElements = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/video/sample-video-elements.html");

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