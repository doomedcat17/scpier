package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

public class ScpWikiData {

    private String title;

    private SCPBranch scpBranch;

    private SCPTranslation scpTranslation;

    private List<ContentNode<?>> content;

    private List<String> tags;

    private Timestamp lastRevisionTimestamp;

    private String author;

    private String originalSource;

    private String translationSource = "";


    public void getContent(ContentNode<?> content) {
        this.content.add(content);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentNode<?>> getContent() {
        return content;
    }

    public void setContent(List<ContentNode<?>> content) {
        this.content = content;
    }

    public String getOriginalSource() {
        return originalSource;
    }

    @JsonIgnore
    public String getContentSource() {
        if (translationSource.isEmpty()) {
            return originalSource;
        } else return translationSource;
    }

    public void setOriginalSource(String originalSource) {
        this.originalSource = originalSource;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public SCPBranch getScpBranch() {
        return scpBranch;
    }

    public void setScpBranch(SCPBranch scpBranch) {
        this.scpBranch = scpBranch;
    }

    public SCPTranslation getScpTranslation() {
        return scpTranslation;
    }

    public void setScpTranslation(SCPTranslation scpTranslation) {
        this.scpTranslation = scpTranslation;
    }

    public Timestamp getLastRevisionTimestamp() {
        return lastRevisionTimestamp;
    }

    public void setLastRevisionTimestamp(Timestamp lastRevisionTimestamp) {
        this.lastRevisionTimestamp = lastRevisionTimestamp;
    }

    public String getTranslationSource() {
        return translationSource;
    }

    public void setTranslationSource(String translationSource) {
        this.translationSource = translationSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScpWikiData)) return false;

        ScpWikiData wikiData = (ScpWikiData) o;

        if (title != null ? !title.equals(wikiData.title) : wikiData.title != null) return false;
        if (scpBranch != wikiData.scpBranch) return false;
        if (scpTranslation != wikiData.scpTranslation) return false;
        if (content != null ? !content.equals(wikiData.content) : wikiData.content != null) return false;
        if (tags != null ? !tags.equals(wikiData.tags) : wikiData.tags != null) return false;
        if (lastRevisionTimestamp != null ? !lastRevisionTimestamp.equals(wikiData.lastRevisionTimestamp) : wikiData.lastRevisionTimestamp != null)
            return false;
        return originalSource != null ? originalSource.equals(wikiData.originalSource) : wikiData.originalSource == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (scpBranch != null ? scpBranch.hashCode() : 0);
        result = 31 * result + (scpTranslation != null ? scpTranslation.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (lastRevisionTimestamp != null ? lastRevisionTimestamp.hashCode() : 0);
        result = 31 * result + (originalSource != null ? originalSource.hashCode() : 0);
        return result;
    }
}
