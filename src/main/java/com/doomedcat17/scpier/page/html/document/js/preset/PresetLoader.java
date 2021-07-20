package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class PresetLoader {

    public static ScpInputPreset loadPreset(String name, String langIdentifier) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(
                    new File("src/main/resources/presets/" + langIdentifier + "/" + name + ".yaml"),
                    ScpInputPreset.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new WikiPresetNotFound(name);
        }
    }
}
