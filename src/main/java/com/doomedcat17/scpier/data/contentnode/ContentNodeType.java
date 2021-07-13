package com.doomedcat17.scpier.data.contentnode;

/** Defines content type of {@link ContentNode}
 * @author Piotr "doomedcat17" Bojczewski */
public enum ContentNodeType {
    /** Content is {@link String} */
    TEXT,
    /** Corresponds to the <b style="color: aqua;">{@code <td> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    TABLE_CELL,
    /** Corresponds to the <b style="color: aqua;">{@code <th> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    TABLE_HEADING_CELL,
    /** Corresponds to the <b style="color: aqua;">{@code <p> }</b> tag form HTML. Content is {@link java.util.List} of {@link TextNode}. */
    PARAGRAPH,
    /** Content is list of {@link ContentNode} of <span style="color: #eb9934;">PARAGRAPH</span> type.*/
    PARAGRAPHS,
    /** Corresponds to the <b style="color: aqua;">{@code <tr> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}
     of <span style="color: #eb9934;">TABLE_CELL</span> or <span style="color: #eb9934;">TABLE_HEADING_CELL</span> type. */
    TABLE_ROW,
    /** Content is {@link java.util.List} of {@link ContentNode} */
    CONTENT_NODES,
    /** Corresponds to the <b style="color: aqua;">{@code <blockquote> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    BLOCKQUOTE,
    /** Corresponds to the <b style="color: aqua;">{@code <ol> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    LIST_OL,
    /** Corresponds to the <b style="color: aqua;">{@code <ul> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    LIST_UL,
    /** Corresponds to the <b style="color: aqua;">{@code <table> }</b> tag form HTML. Content is {@link java.util.List} of {@link ContentNode}.
     * of <span style="color: #eb9934;">TABLE_ROW</span></span> type. */
    TABLE,
    /** Corresponds to the <b style="color: aqua;">{@code <img> }</b> tag form HTML or <b style="color: aqua;">{@code <div class="scp-image-block>}</b>.
     * <p>It defines {@link ContentNode} as {@link ImageNode}</p>*/
    IMAGE,
    /** Corresponds to the <b style="color: aqua;">{@code <h1>-<h6> }</b> tags form HTML.. */
    HEADING,
    /** Corresponds to the <b style="color: aqua;">{@code <div> }</b> tags form HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    DIV,
    /** Corresponds to the audio files/elements from HTML. Content is {@link java.util.List} of {@link TextNode}.
     * <p>It defines {@link ContentNode} as {@link AudioNode}</p>*/
    AUDIO,
    /** Corresponds to the <b style="color: aqua;">{@code <a> }</b> tags form HTML.
     * <p>It defines {@link ContentNode} as {@link AudioNode}</p> */
    HYPERLINK,
    /** Corresponds to the video files/elements from HTML. Content is {@link java.util.List} of {@link TextNode}.
     * <p>It defines {@link ContentNode} as {@link VideoNode}</p>*/
    VIDEO;

    /** Check if given {@link ContentNodeType} is text type.
     *@param contentNodeType type to check*/
    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
