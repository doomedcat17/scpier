package com.doomedcat17.scpier.data.content;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


public abstract class ContentNode<T> implements Serializable {

    private static final long serialVersionUID = 7133209399984473015L;
    protected ContentNodeType contentNodeType;
    protected T content;

    protected ContentNode(ContentNodeType contentNodeType) {
        this.contentNodeType = contentNodeType;
    }

    protected ContentNode(ContentNodeType contentNodeType, T content) {
        this.contentNodeType = contentNodeType;
        this.content = content;
    }

    protected ContentNode() {
    }


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
