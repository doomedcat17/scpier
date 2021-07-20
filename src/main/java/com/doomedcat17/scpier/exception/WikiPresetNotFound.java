package com.doomedcat17.scpier.exception;

public class WikiPresetNotFound extends RuntimeException {

    public WikiPresetNotFound(String presetName) {
        super("Preset not found for "+presetName);
    }
}
