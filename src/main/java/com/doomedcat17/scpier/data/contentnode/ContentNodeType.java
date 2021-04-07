package com.doomedcat17.scpier.data.contentnode;

public enum ContentNodeType {
    TEXT,
    TABLE_CELL,
    TABLE_HEADING_CELL,
    PARAGRAPH,
    PARAGRAPHS,
    TABLE_ROW,
    CONTENT_NODES,
    BLOCKQUOTE,
    LIST_OL,
    LIST_UL,
    TABLE,
    IMAGE,
    APPENDICES,
    ELEMENTS,
    HEADING,
    DIV,
    AUDIO,
    HYPERLINK,
    VIDEO;

    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
