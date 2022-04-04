package com.doomedcat17.scpier.page.html.document.preset;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.html.document.preset.element.WikiElement;

import java.util.ArrayList;
import java.util.List;

public class Preset {

    private String articleName;
    private SCPBranch branch;
    private int runtime = 0;
    private List<WikiElement> wikiElements = new ArrayList<>();
    private List<String> removalDefinitions = new ArrayList<>();
    private List<String> outerContentNames = new ArrayList<>();

    public Preset() {
    }

    public Preset(String articleName, SCPBranch branch, int runtime, List<WikiElement> wikiElements, List<String> removalDefinitions, List<String> outerContentNames) {
        this.articleName = articleName;
        this.branch = branch;
        this.wikiElements = wikiElements;
        this.runtime = runtime;
        this.removalDefinitions = removalDefinitions;
        this.outerContentNames = outerContentNames;
    }

    public List<String> getRemovalDefinitions() {
        return removalDefinitions;
    }

    public String getArticleName() {
        return articleName;
    }

    public SCPBranch getBranch() {
        return branch;
    }

    public List<WikiElement> getWikiElements() {
        return wikiElements;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<String> getOuterContentNames() {
        return outerContentNames;
    }

}
