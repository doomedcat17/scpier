package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.page.html.document.preset.Preset;
import org.jsoup.nodes.Element;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class WikiContent {

    private String name;

    private Element content;

    private String originalSourceUrl;

    private String translationSourceUrl = "";

    private Date lastRevisionTimestamp;

    private List<String> tags;

    private String author;

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

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public Date getLastRevisionTimestamp() {
        return lastRevisionTimestamp;
    }

    public void setLastRevisionTimestamp(Date lastRevisionTimestamp) {
        this.lastRevisionTimestamp = lastRevisionTimestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public WikiContent() {
    }
}
