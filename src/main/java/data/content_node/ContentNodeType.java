package data.content_node;

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
    SCP,
    APPENDICES,
    HEADING,
    DIV;

    public static boolean isText(ContentNodeType contentNodeType) {
        return contentNodeType.equals(ContentNodeType.TEXT) || contentNodeType.equals(ContentNodeType.HEADING);
    }
}
