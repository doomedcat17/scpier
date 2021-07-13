package com.doomedcat17.scpier.data.contentnode;

/** Corresponds to <b style="color: aqua;">{@code <a> }</b> tag from HTML.
 * <p>The content is of {@link String} type and defines text of the hyperlink.</p>*/
public class HyperlinkNode extends TextNode {

    /** URL of hyperlink */
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty() || href.isEmpty();
    }

    public HyperlinkNode(String content, String href) {
        super(content);
        this.href = href;
        contentNodeType = ContentNodeType.HYPERLINK;
    }

    public HyperlinkNode(String content) {
        super(content);
        contentNodeType = ContentNodeType.HYPERLINK;
    }
}