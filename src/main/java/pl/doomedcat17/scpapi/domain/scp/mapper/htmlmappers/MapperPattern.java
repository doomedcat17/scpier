package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

public enum MapperPattern {
    OBJECT_NAME("Item #"),
    OBJECT_CLASS("Object Class"),
    OBJECT_PROCEDURES("Special Containment Procedures"),
    DESCRIPTION("Description");

    public String engNormalized;

    MapperPattern(String engNormalized) {
        this.engNormalized = engNormalized;
    }
}
