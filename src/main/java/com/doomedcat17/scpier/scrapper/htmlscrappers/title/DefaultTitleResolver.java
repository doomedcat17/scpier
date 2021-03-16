package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import java.util.List;

public class DefaultTitleResolver extends TitleResolver {

    private final int MIN_HEADER_LENGTH = 25;

    private final int MAX_HEADER_LENGTH = 69;

    public DefaultTitleResolver(List<String> titlePatterns, List<String> itemNamePattens, List<String> titleIgnored) {
        super(titlePatterns, itemNamePattens, titleIgnored);

    }

    @Override
    public boolean isTitle(String text) {
        if ((titlePatterns.stream().anyMatch(title -> text.contains(title) || text.contains(title.toLowerCase()) || text.contains(title.toUpperCase()))
                || itemNamePattens.stream().anyMatch(text::contains)) && text.length() <= MAX_HEADER_LENGTH) {
            return true;
        } else if (titleIgnored.stream().noneMatch(title -> text.contains(title) || text.contains(title.toLowerCase()) || text.contains(title.toUpperCase()))){
            return (text.length() >= MIN_HEADER_LENGTH && text.length() <= MAX_HEADER_LENGTH);
        } else return false;
    }

    @Override
    public boolean isItemName(String text) {
        return itemNamePattens.stream().anyMatch(text::contains);
    }
}
