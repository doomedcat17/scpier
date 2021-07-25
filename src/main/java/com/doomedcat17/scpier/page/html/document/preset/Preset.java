package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.preset.element.WikiElement;

import java.util.List;

public class Preset {

    private String name;

    private SCPBranch scpBranch;

    private int jsRuntime = 0;

    private List<WikiElement> wikiElements;

    private List<String> removalDefinitions;


    public Preset(String name, SCPBranch scpBranch, int jsRuntime, List<WikiElement> wikiElements, List<String> removalDefinitions) {
        this.name = name;
        this.scpBranch = scpBranch;
        this.wikiElements = wikiElements;
        this.jsRuntime = jsRuntime;
        this.removalDefinitions = removalDefinitions;
    }

    public List<String> getRemovalDefinitions() {
        return removalDefinitions;
    }

    public String getName() {
        return name;
    }

    public SCPBranch getScpBranch() {
        return scpBranch;
    }

    public List<WikiElement> getWikiElements() {
        return wikiElements;
    }

    public int getJsRuntime() {
        return jsRuntime;
    }

    public Preset() {
    }
}
