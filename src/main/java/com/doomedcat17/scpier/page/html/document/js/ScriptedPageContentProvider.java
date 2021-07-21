package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetLoader;
import com.doomedcat17.scpier.page.html.document.js.preset.ScpPreset;

public class ScriptedPageContentProvider {

    private final PresetExecutor presetExecutor;

    public PageContent runJsAndGetContent(String name, String langIdentifier, String src) throws PresetExecutorException {
        ScpPreset scpPreset = PresetLoader.loadPreset(name, langIdentifier);
        return presetExecutor.execute(scpPreset, src);

    }

    public ScriptedPageContentProvider() {
        this.presetExecutor = new PresetExecutor();
    }
}
