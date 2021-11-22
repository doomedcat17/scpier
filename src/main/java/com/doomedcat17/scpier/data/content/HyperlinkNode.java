package com.doomedcat17.scpier.data.content;

import java.util.Map;
public class HyperlinkNode extends TextNode {

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

    public HyperlinkNode(String content, Map<String, String> styles, String href) {
        super(content, styles);
        this.href = href;
        contentNodeType = ContentNodeType.HYPERLINK;
    }

    public HyperlinkNode(String content) {
        super(content);
        contentNodeType = ContentNodeType.HYPERLINK;
    }

    public HyperlinkNode() {
    }
}
