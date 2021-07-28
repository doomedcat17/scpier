package com.doomedcat17.scpier.data.files;

import com.doomedcat17.scpier.data.files.loader.PresetLoader;
import com.doomedcat17.scpier.data.files.loader.RedirectionDefinitionsLoader;
import com.doomedcat17.scpier.data.files.loader.RemovalDefinitionsLoader;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourcesProvider {

    private static PresetProvider presetProvider;

    private static Set<String> removalDefinitions;

    private static Set<String> redirectionDefinitions;

    public static void initResources() throws IOException, URISyntaxException {
        presetProvider = new PresetProvider(PresetLoader.loadPresets());
        RemovalDefinitionsLoader removalDefinitionsLoader = new RemovalDefinitionsLoader();
        removalDefinitions = removalDefinitionsLoader.loadRemovalDefinitions();
        RedirectionDefinitionsLoader redirectionDefinitionsLoader = new RedirectionDefinitionsLoader();
        redirectionDefinitions = redirectionDefinitionsLoader.loadRedirectionDefinitions();
    }

    public static PresetProvider getPresetProvider() {
        return presetProvider;
    }

    public static Set<String> getRemovalDefinitions() {
        return removalDefinitions;
    }

    public static Set<String> getRedirectionDefinitions() {
        return redirectionDefinitions;
    }
}
