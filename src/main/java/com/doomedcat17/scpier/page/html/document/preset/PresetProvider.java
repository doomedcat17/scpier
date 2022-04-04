package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;

import java.util.Optional;
import java.util.Set;

public class PresetProvider {

    private final Set<Preset> presets;

    public Optional<Preset> getPresetByNameAndBranch(String name, SCPBranch scpBranch) {
       return presets.stream()
                .filter(preset -> preset.getArticleName().equals(name))
                .filter(preset -> preset.getBranch().equals(scpBranch))
                .findFirst();

    }

    public PresetProvider(Set<Preset> presets) {
        this.presets = presets;
    }
}
