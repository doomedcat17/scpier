package com.doomedcat17.scpier.data.contentnode;

/** Corresponds to audio files/elements from HTML.
 * <p>The content is of {@link String} type and defines URL of audio file.</p>*/
public class AudioNode extends ContentNode<String> {

    public AudioNode(String content) {
        super(ContentNodeType.AUDIO, content);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() || content.isEmpty();
    }
}
