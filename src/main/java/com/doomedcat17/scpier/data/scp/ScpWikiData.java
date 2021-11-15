package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ScpWikiData {

    private String title;

    private SCPBranch scpBranch;

    private SCPTranslation scpTranslation;

    private List<ContentNode<?>> content;

    private List<String> tags;

    private Date lastRevisionTimestamp;

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

    public Date getLastRevisionTimestamp() {
        return lastRevisionTimestamp;
    }

    public void setLastRevisionTimestamp(Date lastRevisionTimestamp) {
        this.lastRevisionTimestamp = lastRevisionTimestamp;
    }

    public String getTranslationSource() {
        return translationSource;
    }

    public void setTranslationSource(String translationSource) {
        this.translationSource = translationSource;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScpWikiData that = (ScpWikiData) o;

        if (!title.equals(that.title)) return false;
        if (scpBranch != that.scpBranch) return false;
        if (scpTranslation != that.scpTranslation) return false;
        if (!content.equals(that.content)) return false;
        if (!tags.equals(that.tags)) return false;
        if (!lastRevisionTimestamp.equals(that.lastRevisionTimestamp)) return false;
        if (!author.equals(that.author)) return false;
        if (!originalSource.equals(that.originalSource)) return false;
        return translationSource.equals(that.translationSource);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + scpBranch.hashCode();
        result = 31 * result + scpTranslation.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + lastRevisionTimestamp.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + originalSource.hashCode();
        result = 31 * result + translationSource.hashCode();
        return result;
    }
}
