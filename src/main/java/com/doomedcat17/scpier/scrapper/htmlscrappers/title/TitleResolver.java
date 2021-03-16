package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import java.util.List;

public abstract class TitleResolver {

    protected List<String> titlePatterns;

    protected List<String> itemNamePattens;

    protected List<String> titleIgnored;

    public abstract boolean isTitle(String text);

    public abstract boolean isItemName(String text);

    public TitleResolver(List<String> titlePatterns, List<String> itemNamePattens, List<String> titleIgnored) {
        this.titlePatterns = titlePatterns;
        this.itemNamePattens = itemNamePattens;
        this.titleIgnored = titleIgnored;
    }
}
