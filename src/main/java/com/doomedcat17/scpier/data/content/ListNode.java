package com.doomedcat17.scpier.data.content;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public T getLastElement() {
        if (!content.isEmpty()) return content.get(content.size()-1);
        else return null;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }

    public ListNode() {
    }
}
