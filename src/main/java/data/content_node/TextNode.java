package data.content_node;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
@JsonPropertyOrder({"contentNodeType", "styles", "content"})
public class TextNode extends ContentNode<String> {

    private Map<String, String> styles = new HashMap<>();

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

    public TextNode(String content, Map<String, String> styles) {
        super(ContentNodeType.TEXT);
        super.content = content;
        this.styles = styles;
    }




}
