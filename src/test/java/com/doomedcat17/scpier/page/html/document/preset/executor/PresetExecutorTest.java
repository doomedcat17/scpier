package com.doomedcat17.scpier.page.html.document.preset.executor;

import com.doomedcat17.scpier.TestDataProvider;
import com.doomedcat17.scpier.exception.page.html.document.preset.PresetExecutorException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.webclients.NicelyLimitedWebClient;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PresetExecutorTest {

    PresetExecutor presetExecutor = new PresetExecutor(new NicelyLimitedWebClient());

    @Test
    void shouldWaitForJs() throws PresetExecutorException {
        //given
        Preset preset = TestDataProvider.loadPresetFromYAML("src/test/resources/html/test_data/preset/input/shouldWaitForJs.yaml");
        File html = new File("src/test/resources/html/test_data/preset/input/shouldWaitForJs.html");
        //when
        if (preset == null) fail();
        WikiContent wikiContent = presetExecutor.execute(preset, "file://" + html.getAbsolutePath());
        //then
        assertNotNull(wikiContent.getContent().getElementById("jsText"));

    }

    @Test
    void shouldClickTheButton() throws PresetExecutorException {
        //given
        Preset preset = TestDataProvider.loadPresetFromYAML("src/test/resources/html/test_data/preset/input/shouldClickTheButton.yaml");
        File html = new File("src/test/resources/html/test_data/preset/input/shouldClickTheButton.html");
        //when
        if (preset == null) fail();
        WikiContent wikiContent = presetExecutor.execute(preset, "file://" + html.getAbsolutePath());
        //then
        assertNotNull(wikiContent.getContent().getElementById("jsText"));

    }

    @Test
    void shouldClickMultipleButtons() throws PresetExecutorException {
        //given
        Preset preset = TestDataProvider.loadPresetFromYAML("src/test/resources/html/test_data/preset/input/shouldClickMultipleButtons.yaml");
        File html = new File("src/test/resources/html/test_data/preset/input/shouldClickMultipleButtons.html");
        //when
        if (preset == null) fail();
        WikiContent wikiContent = presetExecutor.execute(preset, "file://" + html.getAbsolutePath());
        //then
        Element textElement = wikiContent.getContent().getElementById("jsText");
        assertEquals("First Second Third", textElement.wholeText());

    }

    @Test
    void shouldClickMultipleElements() throws PresetExecutorException {
        //given
        Preset preset = TestDataProvider.loadPresetFromYAML("src/test/resources/html/test_data/preset/input/shouldClickMultipleElements.yaml");
        File html = new File("src/test/resources/html/test_data/preset/input/shouldClickMultipleElements.html");
        //when
        if (preset == null) fail();
        WikiContent wikiContent = presetExecutor.execute(preset, "file://" + html.getAbsolutePath());
        //then
        Element textElement = wikiContent.getContent().getElementById("jsText");
        assertEquals("First Second Third", textElement.wholeText());

    }

    @Test
    void shouldHandleInput() throws PresetExecutorException {
        //given
        Preset preset = TestDataProvider.loadPresetFromYAML("src/test/resources/html/test_data/preset/input/shouldHandleInput.yaml");
        File html = new File("src/test/resources/html/test_data/preset/input/shouldHandleInput.html");
        //when
        if (preset == null) fail();
        WikiContent wikiContent = presetExecutor.execute(preset, "file://" + html.getAbsolutePath());
        //then
        Element textElement = wikiContent.getContent().getElementById("jsText");
        assertEquals("TEST", textElement.wholeText());

    }

}