package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.contentnode.ContentNode;
import com.doomedcat17.scpier.data.contentnode.ContentNodeType;
import com.doomedcat17.scpier.data.contentnode.TextNode;
import com.doomedcat17.scpier.exception.ScpObjectNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ScpObjectNotFoundException, IOException {
        ContentNode<List<TextNode>> paragraph = new ContentNode<>(ContentNodeType.PARAGRAPH, new ArrayList<>());
        System.out.println(paragraph.isEmpty());


    }
}
