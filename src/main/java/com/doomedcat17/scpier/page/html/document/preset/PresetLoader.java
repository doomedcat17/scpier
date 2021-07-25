package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class PresetLoader {

    public static Preset loadPreset(String name, String langIdentifier) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(
                    new File("src/main/resources/presets/" + langIdentifier + "/" + name + ".yaml"),
                    Preset.class);
        } catch (IOException e) {
            throw new WikiPresetNotFound(name);
        }
    }
}