package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ScpWikiData {

    private String title;
    private SCPBranch branch;
    private SCPLanguage language;
    private List<ContentNode<?>> content;
    private List<String> tags;
    private LocalDateTime lastRevisionDate;
    private String author;
    private String originalSource;
    private String translationSource;


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
        if (Objects.isNull(translationSource)) {
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

    public SCPBranch getBranch() {
        return branch;
    }

    public void setBranch(SCPBranch branch) {
        this.branch = branch;
    }

    public SCPLanguage getLanguage() {
        return language;
    }

    public void setLanguage(SCPLanguage language) {
        this.language = language;
    }

    public LocalDateTime getLastRevisionDate() {
        return lastRevisionDate;
    }

    public void setLastRevisionDate(LocalDateTime lastRevisionDate) {
        this.lastRevisionDate = lastRevisionDate;
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
        if (branch != that.branch) return false;
        if (language != that.language) return false;
        if (!content.equals(that.content)) return false;
        if (!tags.equals(that.tags)) return false;
        if (!lastRevisionDate.equals(that.lastRevisionDate)) return false;
        if (!author.equals(that.author)) return false;
        if (!originalSource.equals(that.originalSource)) return false;
        return translationSource.equals(that.translationSource);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + branch.hashCode();
        result = 31 * result + language.hashCode();
        result = 31 * result + content.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + lastRevisionDate.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + originalSource.hashCode();
        result = 31 * result + translationSource.hashCode();
        return result;
    }
}
