package com.doomedcat17.scpier.data.files.loader;

import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class PresetLoader {


    private static byte[] loadFile(String path) throws IOException {
        InputStream in = PresetLoader.class.getClassLoader().getResourceAsStream("presets/"+path);
        return in.readAllBytes();

    }

    public static Set<Preset> loadPresets() throws IOException, URISyntaxException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Set<Preset> presets = new HashSet<>();
        URI uri = PresetLoader.class.getClassLoader().getResource("presets").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            myPath = fileSystem.getPath("preset");
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 2);
        Iterator<Path> it = walk.iterator();
        while (it.hasNext()) {
            Path filePath = it.next();
            if (!Files.isDirectory(filePath)) {
                Preset preset = objectMapper.readValue(loadFile(uri.relativize(filePath.toUri()).toString()), Preset.class);
                presets.add(preset);
            }
        }
        return presets;
    }
}
