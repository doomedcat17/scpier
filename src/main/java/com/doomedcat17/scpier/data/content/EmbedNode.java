package com.doomedcat17.scpier.data.content;


import java.util.ArrayList;
import java.util.List;

public class EmbedNode extends ContentNode<String> {

    /** Caption of the image */
    private List<TextNode> description = new ArrayList<>();

    public List<TextNode> getDescription() {
        return description;
    }

    public void addTextNodeToDescription(TextNode textNode) {
        description.add(textNode);
    }

    public void setDescription(List<TextNode> description) {
        this.description = description;
    }

    public EmbedNode(ContentNodeType contentNodeType, String content, List<TextNode> description) {
        super(contentNodeType, content);
        this.description = description;
    }

    public EmbedNode() {
    }

    public EmbedNode(ContentNodeType contentNodeType, String content) {
        super(contentNodeType, content);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }

}
