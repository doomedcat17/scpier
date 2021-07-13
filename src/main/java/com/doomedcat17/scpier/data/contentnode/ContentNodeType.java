package com.doomedcat17.scpier.data.contentnode;

/** Defines content type of {@link ContentNode}
 * @author Piotr "doomedcat17" Bojczewski */
public enum ContentNodeType {
    /** <p>It defines {@link ContentNode} as {@link TextNode}</p> */
    TEXT,
    /** Corresponds to <b style="color: aqua;">{@code <td> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    TABLE_CELL,
    /** Corresponds to <b style="color: aqua;">{@code <th> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    TABLE_HEADING_CELL,
    /** Corresponds to <b style="color: aqua;">{@code <p> }</b> tag from HTML. Content is {@link java.util.List} of {@link TextNode}. */
    PARAGRAPH,
    /** Content is list of {@link ContentNode} of <span style="color: #eb9934;">PARAGRAPH</span> type.*/
    PARAGRAPHS,
    /** Corresponds to <b style="color: aqua;">{@code <tr> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}
     of <span style="color: #eb9934;">TABLE_CELL</span> or <span style="color: #eb9934;">TABLE_HEADING_CELL</span> type. */
    TABLE_ROW,
    /** Content is {@link java.util.List} of {@link ContentNode} */
    CONTENT_NODES,
    /** Corresponds to <b style="color: aqua;">{@code <blockquote> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    BLOCKQUOTE,
    /** Corresponds to <b style="color: aqua;">{@code <ol> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    LIST_OL,
    /** Corresponds to <b style="color: aqua;">{@code <ul> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    LIST_UL,
    /** Corresponds to <b style="color: aqua;">{@code <table> }</b> tag from HTML. Content is {@link java.util.List} of {@link ContentNode}.
     * of <span style="color: #eb9934;">TABLE_ROW</span></span> type. */
    TABLE,
    /** Corresponds to <b style="color: aqua;">{@code <img> }</b> tag from HTML or <b style="color: aqua;">{@code <div class="scp-image-block>}</b>.
     * <p>It defines {@link ContentNode} as {@link ImageNode}</p>*/
    IMAGE,
    /** Corresponds to <b style="color: aqua;">{@code <h1>-<h6> }</b> tags from HTML.. */
    HEADING,
    /** Corresponds to <b style="color: aqua;">{@code <div> }</b> tags from HTML. Content is {@link java.util.List} of {@link ContentNode}. */
    DIV,
    /** Corresponds to audio files/elements from HTML. Content is {@link java.util.List} of {@link TextNode}.
     * <p>It defines {@link ContentNode} as {@link AudioNode}</p>*/
    AUDIO,
    /** Corresponds to <b style="color: aqua;">{@code <a> }</b> tags from HTML.
     * <p>It defines {@link ContentNode} as {@link HyperlinkNode}</p> */
    HYPERLINK,
    /** Corresponds to video files/elements from HTML. Content is {@link java.util.List} of {@link TextNode}.
     * <p>It defines {@link ContentNode} as {@link VideoNode}</p>*/
    VIDEO;

    /** Check if given {@link ContentNodeType} is text type.
     *@param contentNodeType type to check*/
    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
