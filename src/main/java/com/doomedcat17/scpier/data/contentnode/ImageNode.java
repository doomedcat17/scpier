package com.doomedcat17.scpier.data.contentnode;


import java.util.ArrayList;
import java.util.List;

/** Corresponds to <b style="color: aqua;">{@code <img> }</b> HTML tag  or <b style="color: aqua;">{@code <div class="scp-image-block>}</b>.
 * <p>The content is of {@link String} type and defines URL of the image</p>*/
public class ImageNode extends ContentNode<String> {

    /** Caption of the image */
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

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }

}
