package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.contentnode.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class ScpWikiData {

    private String title;

    private List<ContentNode<?>> content = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScpWikiData)) return false;

        ScpWikiData scpWikiData = (ScpWikiData) o;

        if (getTitle() != null ? !getTitle().equals(scpWikiData.getTitle()) : scpWikiData.getTitle() != null)
            return false;
        if (getContent() != null ? !getContent().equals(scpWikiData.getContent()) : scpWikiData.getContent() != null)
            return false;
        if (getTags() != null ? !getTags().equals(scpWikiData.getTags()) : scpWikiData.getTags() != null) return false;
        return getSource() != null ? getSource().equals(scpWikiData.getSource()) : scpWikiData.getSource() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
    }
}
