package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.preset.Preset;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetLoader;
import com.doomedcat17.scpier.page.html.document.js.preset.executor.PresetExecutor;

public class ScriptedPageContentProvider {

    private final PresetExecutor presetExecutor;

    public PageContent runJsAndGetContent(String name, String langIdentifier, String src) throws PresetExecutorException {
        Preset preset = PresetLoader.loadPreset(name, langIdentifier);
        return presetExecutor.execute(preset, src);

    }

    public ScriptedPageContentProvider() {
        this.presetExecutor = new PresetExecutor();
    }
}
