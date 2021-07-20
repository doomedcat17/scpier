package com.doomedcat17.scpier.page.html.document.js;

import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.doomedcat17.scpier.page.PageContent;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetExecutor;
import com.doomedcat17.scpier.page.html.document.js.preset.PresetLoader;
import com.doomedcat17.scpier.page.html.document.js.preset.ScpInputPreset;

import java.io.IOException;

public class ScriptedPageContentProvider {

    private final PresetExecutor presetExecutor;

    public PageContent runJsAndGetContent (String name, String langIdentifier, String src) throws IOException {
        try {
            ScpInputPreset scpInputPreset = PresetLoader.loadPreset(name, langIdentifier);
            return presetExecutor.execute(scpInputPreset, src);
        } catch (WikiPresetNotFound e) {
            e.printStackTrace();
            ScriptedHTMLDocumentProvider scriptedHTMLDocumentProvider = new ScriptedHTMLDocumentProvider();
            return scriptedHTMLDocumentProvider.getWebpageContent(src);
        }
    }

    public ScriptedPageContentProvider() {
        this.presetExecutor = new PresetExecutor();
    }
}
