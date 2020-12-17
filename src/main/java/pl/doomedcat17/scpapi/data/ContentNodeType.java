package pl.doomedcat17.scpapi.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContentNodeType {
    TEXT,
    ELEMENTS,
    BLOCKQUOTE,
    LIST_OL,
    LIST_UL,
    TEXT_DELETED,
    TABLE,
    IMAGE
}
