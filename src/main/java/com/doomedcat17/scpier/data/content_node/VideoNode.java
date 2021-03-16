package com.doomedcat17.scpier.data.content_node;

import java.util.ArrayList;
import java.util.List;

public class VideoNode extends ContentNode<String> {

    public VideoNode(String content) {
        super(ContentNodeType.VIDEO, content);
    }

    private List<TextNode> caption = new ArrayList<>();

    public void addTextNodeToCaption(TextNode textNode) {
        caption.add(textNode);
    }

    public List<TextNode> getCaption() {
        return caption;
    }

    public void setCaption(List<TextNode> caption) {
        this.caption = caption;
    }
}
