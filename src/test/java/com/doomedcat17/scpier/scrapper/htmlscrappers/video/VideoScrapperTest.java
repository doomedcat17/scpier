package com.doomedcat17.scpier.scrapper.htmlscrappers.video;

import com.doomedcat17.scpapi.TestDataProvider;
import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.VideoNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.ScrapperTest;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoScrapperTest extends ScrapperTest {

    private VideoScrapper videoScrapper = new VideoScrapper(SOURCE, titleResolver);

    private final Element sampleElements = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/video/sampleVideoElements.html");

    @Test
    void shouldScrapVideo() {
        //given
        Element videoElement = sampleElements.getElementById("shouldScrapVideo");
        //when
        Appendix appendix = videoScrapper.scrapElement(videoElement);
        //then
        assertTrue(appendix.getContents().get(0) instanceof VideoNode);
        assertEquals("http://kontainer.djkakt.us/local--files/popsioak-6/vid", appendix.getContents().get(0).getContent());
    }

}