package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;

import java.util.List;

public class ScpInputPreset {

    private final String name;

    private final SCPBranch scpBranch;

    private final List<WikiElement> wikiElements;


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
}
