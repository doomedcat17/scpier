package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.page.html.document.preset.Preset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class WikiContent{

    private String name;
    private String title;
    private Element content;
    private String originalSourceUrl;
    private String translationSourceUrl;
    private LocalDateTime lastRevisionDate;
    private List<String> tags;
    private Preset preset;

    public WikiContent(Element content) {
        this.content = content;
    }

    public WikiContent() {
    }

    public WikiContent(WikiContent wikiContent) {
        this.name = wikiContent.getName();
        this.title = wikiContent.getTitle();
        this.originalSourceUrl = wikiContent.getOriginalSourceUrl();
        this.translationSourceUrl = wikiContent.getTranslationSourceUrl();
        this.lastRevisionDate = wikiContent.getLastRevisionDate();
        this.preset = wikiContent.getPreset();
        if (Objects.nonNull(wikiContent.getTags()))
            this.tags = List.copyOf(wikiContent.getTags());
        if (Objects.nonNull(wikiContent.getContent()))
            this.content = Jsoup.parse(wikiContent.getContent().outerHtml());
    }


    public String getContentSource() {
        if (Objects.isNull(translationSourceUrl) || translationSourceUrl.isBlank()) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public LocalDateTime getLastRevisionDate() {
        return lastRevisionDate;
    }

    public void setLastRevisionDate(LocalDateTime lastRevisionDate) {
        this.lastRevisionDate = lastRevisionDate;
    }

}
