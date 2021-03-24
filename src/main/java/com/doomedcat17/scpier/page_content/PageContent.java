package com.doomedcat17.scpier.page_content;

import org.jsoup.nodes.Element;

import java.util.List;

public class PageContent {

    private String name;

    private Element content;

    private String langIdentifier;

    private String sourceUrl;

    private List<String> tags;

    public String getLangIdentifier() {
        return langIdentifier;
    }

    public void setLangIdentifier(String langIdentifier) {
        this.langIdentifier = langIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Element getContent() {
        return content;
    }

    public void setContent(Element content) {
        this.content = content;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
