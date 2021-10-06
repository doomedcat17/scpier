package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.page.html.document.preset.Preset;
import org.jsoup.nodes.Element;

import java.sql.Timestamp;
import java.util.List;

public class WikiContent {

    private String name;

    private Element content;

    private String translationIdentifier;

    private String langIdentifier;

    private String originalSourceUrl;

    private String translationSourceUrl = "";

    private Timestamp lastRevisionTimestamp;

    private List<String> tags;

    private Preset preset;

    public String getContentSource() {
        if (translationSourceUrl.isEmpty()) {
            return originalSourceUrl;
        } else return translationSourceUrl;
    }

    public String getTranslationSourceUrl() {
        return translationSourceUrl;
    }

    public void setTranslationSourceUrl(String translationSourceUrl) {
        this.translationSourceUrl = translationSourceUrl;
    }

    public String getTranslationIdentifier() {
        return translationIdentifier;
    }

    public void setTranslationIdentifier(String translationIdentifier) {
        this.translationIdentifier = translationIdentifier;
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

    public String getOriginalSourceUrl() {
        return originalSourceUrl;
    }

    public void setOriginalSourceUrl(String originalSourceUrl) {
        this.originalSourceUrl = originalSourceUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public WikiContent(Element content) {
        this.content = content;
    }

    public String getLangIdentifier() {
        return langIdentifier;
    }

    public void setLangIdentifier(String langIdentifier) {
        this.langIdentifier = langIdentifier;
    }

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public Timestamp getLastRevisionTimestamp() {
        return lastRevisionTimestamp;
    }

    public void setLastRevisionTimestamp(Timestamp lastRevisionTimestamp) {
        this.lastRevisionTimestamp = lastRevisionTimestamp;
    }

    public WikiContent() {
    }
}
