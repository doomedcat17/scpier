package com.doomedcat17.scpier.data.files;

import com.doomedcat17.scpier.data.files.loader.JsonResourceLoader;
import com.doomedcat17.scpier.data.files.loader.OffsetPatternsLoader;
import com.doomedcat17.scpier.data.files.loader.PresetLoader;
import com.doomedcat17.scpier.page.html.document.preset.PresetProvider;
import com.doomedcat17.scpier.page.html.document.provider.offset.OffsetPattern;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class ResourcesProvider {

    private static final String REDIRECTION_DEFINITIONS_PATH = "redirectionElementsDefinitions.json";
    private static final String REMOVAL_DEFINITIONS_PATH = "removalElementsDefinitions.json";
    private static final String TRASH_IFRAMES_DEFINITIONS_PATH = "trashIframesDefinitions.json";
    private static PresetProvider presetProvider;
    private static Set<String> removalDefinitions;
    private static Set<String> redirectionDefinitions;
    private static Set<String> trashIframesDefinitions;
    private static Set<OffsetPattern> offsetPatterns;
    private volatile static boolean initialized = false;

    public static synchronized void initResources() throws IOException, URISyntaxException {
        if (!initialized) {
            presetProvider = new PresetProvider(PresetLoader.loadPresets());
            JsonResourceLoader jsonResourceLoader = new JsonResourceLoader();
            removalDefinitions = jsonResourceLoader.loadSet(REMOVAL_DEFINITIONS_PATH);
            redirectionDefinitions = jsonResourceLoader.loadSet(REDIRECTION_DEFINITIONS_PATH);
            trashIframesDefinitions = jsonResourceLoader.loadSet(TRASH_IFRAMES_DEFINITIONS_PATH);
            OffsetPatternsLoader offsetPatternsLoader = new OffsetPatternsLoader();
            offsetPatterns = offsetPatternsLoader.loadOffsetPatterns();
            initialized = true;
        }
    }

    public static boolean isInitialized() {
        return initialized;
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

    public static Set<OffsetPattern> getOffsetPatterns() {
        return offsetPatterns;
    }

    public static Set<String> getTrashIframesDefinitions() {
        return trashIframesDefinitions;
    }
}
