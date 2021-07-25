package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.preset.executor.PresetExecutor;

public class PresetWikiContentProvider {

    private final PresetExecutor presetExecutor;

    public WikiContent runJsAndGetContent(String name, String langIdentifier, String src) throws PresetExecutorException {
        Preset preset = PresetLoader.loadPreset(name, langIdentifier);
        return presetExecutor.execute(preset, src);

    }

    public PresetWikiContentProvider() {
        this.presetExecutor = new PresetExecutor();
    }
}
