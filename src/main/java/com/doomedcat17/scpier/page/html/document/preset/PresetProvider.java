package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.exception.page.html.document.preset.WikiPresetNotFoundException;

import java.util.Set;

public class PresetProvider {

    private final Set<Preset> presets;

    public Preset getPresetByNameAndBranch(String name, SCPBranch scpBranch) throws WikiPresetNotFoundException {
       return presets.stream()
                .filter(preset -> preset.getName().equals(name))
                .filter(preset -> preset.getScpBranch().equals(scpBranch))
                .findFirst().orElseThrow(() -> new WikiPresetNotFoundException(name));

    }

    public PresetProvider(Set<Preset> presets) {
        this.presets = presets;
    }
}
