package com.doomedcat17.scpier.data.contentnode;

public class AudioNode extends ContentNode<String> {

    public AudioNode(String content) {
        super(ContentNodeType.AUDIO, content);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }
}
