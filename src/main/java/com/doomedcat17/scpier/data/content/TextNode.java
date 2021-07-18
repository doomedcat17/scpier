package com.doomedcat17.scpier.data.content;

import java.util.HashMap;
import java.util.Map;
/** Text piece of data*/
public class TextNode extends ContentNode<String> {

    /** {@link Map} of CSS styles applied to text. <span style="color: orange;">Only local styles are applied</span>*/
    protected Map<String, String> styles = new HashMap<>();

    public void addStyle(String styleName, String styleValue) {
        styles.put(styleName, styleValue);
    }

    public void addStyles(Map<String, String> styles) {
        this.styles.putAll(styles);
    }

    public TextNode() {
        super(ContentNodeType.TEXT);
    }

    public TextNode(String content) {
        super(ContentNodeType.TEXT);
        super.content = content;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }

    public TextNode(String content, Map<String, String> styles) {
        super(ContentNodeType.TEXT);
        super.content = content;
        this.styles = styles;
    }

    public Map<String, String> getStyles() {
        return styles;
    }

    public void setStyles(Map<String, String> styles) {
        this.styles = styles;
    }
}
