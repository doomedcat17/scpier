package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class PresetExecutor {





    public static ScpInputPreset loadPreset(String name, SCPBranch scpBranch) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(
                    new File("src/main/resources/presets/" + scpBranch.identifier + "/" + name + ".yaml"),
                    ScpInputPreset.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Preset not found for: "+name);
        }
    }
}
