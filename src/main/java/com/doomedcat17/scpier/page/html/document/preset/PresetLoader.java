package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.exception.WikiPresetNotFound;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class PresetLoader {

    public Preset loadPreset(String name, String langIdentifier) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            String source = "src/main/resources/presets/" + langIdentifier + "/" + name.toLowerCase(Locale.ROOT) + ".yaml";
            return mapper.readValue(
                    new File(source),
                    Preset.class);
        } catch (IOException e) {
            throw new WikiPresetNotFound(name);
        }
    }
}
