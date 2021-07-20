package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.exception.PresetExecutorException;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetLoader;
import com.doomedcat17.scpier.page.html.document.js.preset.ScpInputPreset;

public class ScriptedPageContentProvider {

    private final PresetExecutor presetExecutor;

    public PageContent runJsAndGetContent(String name, String langIdentifier, String src) throws PresetExecutorException {
        ScpInputPreset scpInputPreset = PresetLoader.loadPreset(name, langIdentifier);
        return presetExecutor.execute(scpInputPreset, src);

    }

    public ScriptedPageContentProvider() {
        this.presetExecutor = new PresetExecutor();
    }
}
