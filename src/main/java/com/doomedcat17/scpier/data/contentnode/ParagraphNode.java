package com.doomedcat17.scpier.data.contentnode;

import java.util.ArrayList;
import java.util.List;

public class ParagraphNode extends ListNode<TextNode>{
    public ParagraphNode(List<TextNode> content) {
        super(ContentNodeType.PARAGRAPH, content);
    }

    public ParagraphNode() {
        super(ContentNodeType.PARAGRAPH, new ArrayList<>());
    }
}
