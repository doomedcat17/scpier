package com.doomedcat17.scpier.data.content_node;

public enum ContentNodeType {
    TEXT,
    TABLE_CELL,
    TABLE_HEADING_CELL,
    PARAGRAPH,
    TABLE_ROW,
    ELEMENTS,
    BLOCKQUOTE,
    LIST_OL,
    LIST_UL,
    TABLE,
    IMAGE,
    APPENDICES,
    HEADING,
    DIV,
    AUDIO,
    HYPERLINK,
    VIDEO;

    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
