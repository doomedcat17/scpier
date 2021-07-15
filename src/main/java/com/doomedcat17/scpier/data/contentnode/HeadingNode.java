package com.doomedcat17.scpier.data.contentnode;

import java.util.ArrayList;
import java.util.List;

public class HeadingNode extends ListNode<TextNode>{
    public HeadingNode(List<TextNode> content) {
        super(ContentNodeType.HEADING, content);
    }

    public HeadingNode() {
        super(ContentNodeType.HEADING, new ArrayList<>());
    }

}
