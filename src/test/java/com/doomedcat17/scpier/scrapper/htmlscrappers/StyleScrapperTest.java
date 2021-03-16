package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpapi.TestDataProvider;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import com.doomedcat17.scpier.scrapper.htmlscrappers.text.StyleScrapper;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StyleScrapperTest {

    private final Element sampleLines = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/lines/SampleLinesElements.html");

    @Test
    void shouldScrapStylesFromStrongElement() {
        //given
        Element strongElement = sampleLines.getElementById("sampleStrong");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(strongElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-weight")),
                () -> assertEquals("bold", styles.get("font-weight"))
        );

    }

    @Test
    void shouldScrapStylesFromStrongElementWithStyleAttribute() {
        //given
        Element strongElement = sampleLines.getElementById("shouldScrapStylesFromStrongElementWithStyleAttribute");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(strongElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-weight")),
                () -> assertEquals("bold", styles.get("font-weight")),
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style"))
        );

    }

    @Test
    void shouldScrapStylesFromStrongElementWithStyleAttribute2() {
        //given
        Element strongElement = sampleLines.getElementById("shouldScrapStylesFromStrongElementWithStyleAttribute2");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(strongElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-weight")),
                () -> assertEquals("bold", styles.get("font-weight")),
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style")),
                () -> assertTrue(styles.containsKey("text-decoration")),
                () -> assertEquals("line-through", styles.get("text-decoration"))
        );

    }

    @Test
    void shouldScrapStylesFromSpanElementWithStyleAttribute() {
        //given
        Element spanElement = sampleLines.getElementById("shouldScrapStylesFromSpanElementWithStyleAttribute");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(spanElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style")),
                () -> assertTrue(styles.containsKey("text-decoration")),
                () -> assertEquals("line-through", styles.get("text-decoration"))
        );

    }

    @Test
    void shouldScrapStylesFromSubElement() {
        //given
        Element subElement = sampleLines.getElementById("shouldScrapStylesFromSubElement");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(subElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style")),
                () -> assertTrue(styles.containsKey("text-decoration")),
                () -> assertEquals("line-through", styles.get("text-decoration")),
                () -> assertTrue(styles.containsKey("vertical-align")),
                () -> assertEquals("sub", styles.get("vertical-align")),
                () -> assertTrue(styles.containsKey("font-size")),
                () -> assertEquals("smaller", styles.get("font-size"))
        );

    }

    @Test
    void shouldScrapStylesFromSupElement() {
        //given
        Element subElement = sampleLines.getElementById("shouldScrapStylesFromSupElement");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(subElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style")),
                () -> assertTrue(styles.containsKey("text-decoration")),
                () -> assertEquals("line-through", styles.get("text-decoration")),
                () -> assertTrue(styles.containsKey("vertical-align")),
                () -> assertEquals("super", styles.get("vertical-align")),
                () -> assertTrue(styles.containsKey("font-size")),
                () -> assertEquals("smaller", styles.get("font-size"))
        );

    }

    @Test
    void shouldScrapStylesFromIElement() {
        //given
        Element iElement = sampleLines.getElementById("shouldScrapStylesFromIElement");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(iElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style"))
        );

    }

    @Test
    void shouldScrapStylesFromEmElement() {
        //given
        Element emElement = sampleLines.getElementById("shouldScrapStylesFromEmElement");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(emElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("font-style")),
                () -> assertEquals("italic", styles.get("font-style"))
        );

    }
    @Test
    void shouldScrapStylesFromPElement() {
        //given
        Element emElement = sampleLines.getElementById("shouldScrapContentWithStylization");
        //when
        Map<String, String> styles = StyleScrapper.scrapStyles(emElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("color")),
                () -> assertEquals("red", styles.get("color"))
        );

    }

}