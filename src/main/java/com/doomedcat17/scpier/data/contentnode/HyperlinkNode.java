package com.doomedcat17.scpier.data.contentnode;

public class HyperlinkNode extends TextNode {

    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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
