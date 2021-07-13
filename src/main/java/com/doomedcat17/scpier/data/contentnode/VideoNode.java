package com.doomedcat17.scpier.data.contentnode;

import java.util.ArrayList;
import java.util.List;

/** Corresponds to video files/elements from HTML.
 * <p>The content is of {@link String} type and defines URL of the image</p>*/
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

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }
}
