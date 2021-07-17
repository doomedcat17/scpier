package com.doomedcat17.scpier.data.content;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/** Smallest piece of data. It consists of the {@link ContentNodeType} and the actual data. Data type depends on {@link ContentNodeType}
 * @author Piotr "doomedcat17" Bojczewski */

public abstract class ContentNode<T> implements Serializable {

    protected ContentNodeType contentNodeType;

    protected T content;

    @JsonIgnore
    public boolean isEmpty() {
        return content == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContentNode)) return false;

        ContentNode<?> that = (ContentNode<?>) o;

        if (getContentNodeType() != that.getContentNodeType()) return false;
        return getContent() != null ? getContent().equals(that.getContent()) : that.getContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getContentNodeType() != null ? getContentNodeType().hashCode() : 0;
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }

    public ContentNode(ContentNodeType contentNodeType) {
        this.contentNodeType = contentNodeType;
    }

    public ContentNode(ContentNodeType contentNodeType, T content) {
        this.contentNodeType = contentNodeType;
        this.content = content;
    }

    public ContentNode() {
    }

    public ContentNodeType getContentNodeType() {
        return contentNodeType;
    }

    public void setContentNodeType(ContentNodeType contentNodeType) {
        this.contentNodeType = contentNodeType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
