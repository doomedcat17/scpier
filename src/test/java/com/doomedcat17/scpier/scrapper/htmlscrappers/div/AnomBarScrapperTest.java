package com.doomedcat17.scpier.scrapper.htmlscrappers.div;

import com.doomedcat17.scpier.data.appendix.Appendix;
import com.doomedcat17.scpier.data.content_node.ContentNode;
import com.doomedcat17.scpier.data.content_node.TextNode;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts.AnomBarScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.componetnts.DivScrapperComponent;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnomBarScrapperTest extends DivScrapperTest {

    DivScrapperComponent divScrapperComponent = new AnomBarScrapper(SOURCE, titleResolver);


    @Test
    void shouldMapAnomBar() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar");
        //when
        List<Appendix> appendices = divScrapperComponent.scrapDivContent(anomBarElement);
        //then
        try {
            ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) appendices.get(0).getContents().get(0);
            assertEquals(4, appendices.size());
            assertEquals("Containment Class", appendices.get(0).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Esoteric", paragraph.getContent().get(0).getContent());

            paragraph = (ContentNode<List<TextNode>>) appendices.get(1).getContents().get(0);
            assertEquals("Secondary Class", appendices.get(1).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Thaumiel", paragraph.getContent().get(0).getContent());

            paragraph = (ContentNode<List<TextNode>>) appendices.get(2).getContents().get(0);
            assertEquals("Disruption Class", appendices.get(2).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Ekhi", paragraph.getContent().get(0).getContent());

            paragraph = (ContentNode<List<TextNode>>) appendices.get(3).getContents().get(0);
            assertEquals("Risk Class", appendices.get(3).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Notice", paragraph.getContent().get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void shouldMapAnomBar2() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar2");
        //when
        List<Appendix> appendices = divScrapperComponent.scrapDivContent(anomBarElement);
        //then
        try {
            ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) appendices.get(0).getContents().get(0);
            assertEquals(3, appendices.size());
            assertEquals("Containment Class", appendices.get(0).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Euclid", paragraph.getContent().get(0).getContent());

            paragraph = (ContentNode<List<TextNode>>) appendices.get(1).getContents().get(0);
            assertEquals("Disruption Class", appendices.get(1).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Vlam", paragraph.getContent().get(0).getContent());

            paragraph = (ContentNode<List<TextNode>>) appendices.get(2).getContents().get(0);
            assertEquals("Risk Class", appendices.get(2).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Notice", paragraph.getContent().get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void shouldMapAnomBar3() {
        //given
        Element anomBarElement = sampleDivs.getElementById("shouldMapAnomBar3");
        //when
        List<Appendix> appendices = divScrapperComponent.scrapDivContent(anomBarElement);
        //then
        try {
            ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) appendices.get(0).getContents().get(0);
            assertEquals(1, appendices.size());
            assertEquals("Containment Class", appendices.get(0).getTitle());
            assertEquals(1, paragraph.getContent().size());
            assertEquals("Pending", paragraph.getContent().get(0).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}