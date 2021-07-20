package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.js.preset.elements.WikiElement;

import java.util.List;

public class ScpInputPreset {

    private String name;

    private SCPBranch scpBranch;

    private String test;

    private List<WikiElement> wikiElements;


    public ScpInputPreset(String name, SCPBranch scpBranch, List<WikiElement> wikiElements) {
        this.name = name;
        this.scpBranch = scpBranch;
        this.wikiElements = wikiElements;
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

    public ScpInputPreset() {
    }
}
