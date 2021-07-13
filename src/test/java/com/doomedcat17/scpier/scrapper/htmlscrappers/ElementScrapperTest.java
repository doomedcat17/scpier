package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.scrapper.ElementContentScrapper;
import com.doomedcat17.scpier.scrapper.ElementScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElementScrapperTest extends ScrapperTest  {

    private final ElementScrapper elementScrapper = new DivScrapper(SOURCE);

    private final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/testdata/divs/SampleDivsElements.html");

    @Test
    void shouldScrapMath() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(divElement, SOURCE);
        //then
        ContentNode<List<TextNode>> paragraph = (ContentNode<List<TextNode>>) contentNodes.get(0);
        StringBuilder stringBuilder = new StringBuilder();
        paragraph.getContent().forEach(textNode -> stringBuilder.append(textNode.getContent()));
        assertEquals(divElement.text(), stringBuilder.toString());
    }

    @Test
    void shouldScrapMath2() {
        //given
        Element divElement = sampleDivs.getElementById("shouldScrapMath2");
        //when
        List<ContentNode<?>> contentNodes = ElementContentScrapper.scrapContent(divElement, SOURCE);
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