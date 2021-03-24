package com.doomedcat17.scpier.data.tale;

import com.doomedcat17.scpier.data.content_node.ContentNode;

import java.util.ArrayList;
import java.util.List;

public class ScpTale {

    private String title;

    private List<ContentNode<?>> content = new ArrayList<>();

    private List<String> tags;

    private String source;

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
