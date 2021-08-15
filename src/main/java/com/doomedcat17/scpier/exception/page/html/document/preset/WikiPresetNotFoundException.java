package com.doomedcat17.scpier.exception.page.html.document.preset;

public class WikiPresetNotFoundException extends RuntimeException {

    public WikiPresetNotFoundException(String presetName) {
        super("Preset not found for "+presetName);
    }

}
