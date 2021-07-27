package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.preset.element.WikiElement;

import java.util.ArrayList;
import java.util.List;

public class Preset {

    private String name;

    private SCPBranch scpBranch;

    private int jsRuntime = 0;

    private List<WikiElement> wikiElements = new ArrayList<>();

    private List<String> removalDefinitions = new ArrayList<>();

    private List<String> outerContentNames = new ArrayList<>();


    public Preset(String name, SCPBranch scpBranch, int jsRuntime, List<WikiElement> wikiElements, List<String> removalDefinitions, List<String> outerContentNames) {
        this.name = name;
        this.scpBranch = scpBranch;
        this.wikiElements = wikiElements;
        this.jsRuntime = jsRuntime;
        this.removalDefinitions = removalDefinitions;
        this.outerContentNames = outerContentNames;
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

    public List<String> getOuterContentNames() {
        return outerContentNames;
    }

    public Preset() {
    }
}
