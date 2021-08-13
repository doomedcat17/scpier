package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.content.ContentNode;

import java.util.List;

public class ScpWikiData {

    //TODO add lastRevision

    private String title;

    private SCPBranch scpBranch;

    private SCPTranslation scpTranslation;

    private List<ContentNode<?>> content;

    private List<String> tags;

    private String source;


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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScpWikiData)) return false;

        ScpWikiData that = (ScpWikiData) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (scpBranch != that.scpBranch) return false;
        if (scpTranslation != that.scpTranslation) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        return source != null ? source.equals(that.source) : that.source == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (scpBranch != null ? scpBranch.hashCode() : 0);
        result = 31 * result + (scpTranslation != null ? scpTranslation.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
