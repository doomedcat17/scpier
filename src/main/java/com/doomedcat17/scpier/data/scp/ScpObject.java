package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.data.contentnode.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class ScpObject {

    private String objectName;

    private List<ContentNode<?>> content = new ArrayList<>();

    private List<String> tags;

    private String source;

    public void addContent(ContentNode<?> content) {
        this.content.add(content);
    }

    public void getContent(ContentNode<?> content) {
        this.content.add(content);
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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
        if (!(o instanceof ScpObject)) return false;

        ScpObject scpObject = (ScpObject) o;

        if (getObjectName() != null ? !getObjectName().equals(scpObject.getObjectName()) : scpObject.getObjectName() != null)
            return false;
        if (getContent() != null ? !getContent().equals(scpObject.getContent()) : scpObject.getContent() != null)
            return false;
        if (getTags() != null ? !getTags().equals(scpObject.getTags()) : scpObject.getTags() != null) return false;
        return getSource() != null ? getSource().equals(scpObject.getSource()) : scpObject.getSource() == null;
    }

    @Override
    public int hashCode() {
        int result = getObjectName() != null ? getObjectName().hashCode() : 0;
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        return result;
    }
}
