package com.doomedcat17.scpier.scrappers;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.scraper.text.StyleScraper;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StyleScrapperTest {

    private final Element sampleLines = TestDataProvider
            .getSampleElement("src/test/resources/html/test_data/lines/sample-lines-elements.html");

    @Test
    void shouldScrapStylesFromStrongElement() {
        //given
        Element strongElement = sampleLines.getElementById("sampleStrong");
        //when
        Map<String, String> styles = StyleScraper.scrapStyles(strongElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(strongElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(strongElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(spanElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(subElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(subElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(iElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(emElement);
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
        Map<String, String> styles = StyleScraper.scrapStyles(emElement);
        //then
        assertAll(
                () -> assertTrue(styles.containsKey("color")),
                () -> assertEquals("red", styles.get("color"))
        );

    }

}