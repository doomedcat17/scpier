package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.doomedcat17.scpier.data.content.TextNode;
import com.doomedcat17.scpier.scraper.ElementContentScraper;
import com.doomedcat17.scpier.scraper.ElementScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.testbox.TestDataProvider;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementScraperTest extends ScrapperTest  {

    private final ElementScraper elementScraper = new DivScraper(SOURCE);

    private final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/divs/sample-divs-element.html");

    @Test
    void shouldScrapMath()  {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(divElement, SOURCE);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMath2()  {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath2");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScraper.scrapContent(divElement, SOURCE);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMaterialBox() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMaterialBox");
        //when
      //  List<Appendix> appendices = AppendixMapper.mapContentNodesToAppendices(divElement.childNodes(), titleResolver);
        //then
        try {
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        } catch (Exception ignored) {}
    }




}