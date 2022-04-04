package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.content.ContentNode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ScpWikiData {

    private String name;
    private String title;
    private SCPBranch branch;
    private SCPLanguage language;
    private List<String> tags;
    private LocalDateTime lastRevisionDate;
    private String originalSource;
    private String translationSource;
    private List<ContentNode<?>> content;

    public ScpWikiData() {
    }

    public ScpWikiData(String name, String title, SCPBranch branch, SCPLanguage language, List<String> tags, LocalDateTime lastRevisionDate, String originalSource, String translationSource, List<ContentNode<?>> content) {
        this.name = name;
        this.title = title;
        this.branch = branch;
        this.language = language;
        this.tags = tags;
        this.lastRevisionDate = lastRevisionDate;
        this.originalSource = originalSource;
        this.translationSource = translationSource;
        this.content = content;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTranslationSource(String translationSource) {
        this.translationSource = translationSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScpWikiData that = (ScpWikiData) o;
        return Objects.equals(title, that.title) && branch == that.branch && language == that.language && Objects.equals(content, that.content) && Objects.equals(tags, that.tags) && Objects.equals(lastRevisionDate, that.lastRevisionDate) && Objects.equals(originalSource, that.originalSource) && Objects.equals(translationSource, that.translationSource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, branch, language, content, tags, lastRevisionDate, originalSource, translationSource);
    }
}
