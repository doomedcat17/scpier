package pl.doomedcat17.scpapi.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContentNodeType {
    TEXT,
    PARAGRAPH,
    ROW,
    ELEMENTS,
    BLOCKQUOTE,
    LIST_OL,
    LIST_UL,
    TEXT_DELETED,
    TABLE,
    IMAGE,
    SCP,
    APPENDICES,
    HEADING,
    DIV;

    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
