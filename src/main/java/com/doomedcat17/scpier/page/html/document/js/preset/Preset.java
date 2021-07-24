package com.doomedcat17.scpier.page.html.document.js.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.js.preset.element.WikiElement;

import java.util.List;

public class Preset {

    private String name;

    private SCPBranch scpBranch;

    private int jsRuntime;

    private List<WikiElement> wikiElements;


    public Preset(String name, SCPBranch scpBranch, int jsRuntime, List<WikiElement> wikiElements) {
        this.name = name;
        this.scpBranch = scpBranch;
        this.wikiElements = wikiElements;
        this.jsRuntime = jsRuntime;
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
