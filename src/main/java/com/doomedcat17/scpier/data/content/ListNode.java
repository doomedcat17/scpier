package com.doomedcat17.scpier.data.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListNode<T> extends ContentNode<List<T>>{

    public ListNode(ContentNodeType contentNodeType, List<T> content) {
        super(contentNodeType, new ArrayList<>(content));
    }

    public ListNode(ContentNodeType contentNodeType) {
        super(contentNodeType, new ArrayList<>());
    }

    public void addElement(T element) {
        content.add(element);
    }

    public void addElements(Collection<T> elements) {
        content.addAll(elements);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }

    public ListNode() {
    }
}
