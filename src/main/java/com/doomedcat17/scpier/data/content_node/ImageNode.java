package com.doomedcat17.scpier.data.content_node;


import java.util.ArrayList;
import java.util.List;

public class ImageNode extends ContentNode<String> {

    private List<TextNode> caption = new ArrayList<>();

    public List<TextNode> getCaption() {
        return caption;
    }

    public void addTextNodeToCaption(TextNode textNode) {
        caption.add(textNode);
    }

    public void setCaption(List<TextNode> caption) {
        this.caption = caption;
    }

    public ImageNode() {
        contentNodeType = ContentNodeType.IMAGE;
    }


    public ImageNode(String content, List<TextNode> caption) {
        super(ContentNodeType.IMAGE, content);
        this.caption = caption;
    }

}
