package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

public enum ScpPattern {
    OBJECT_NAME("Item #"),
    OBJECT_CLASS("Object Class"),
    OBJECT_PROCEDURES("Special Containment Procedures"),
    DESCRIPTION("Description");

    public String engNormalized;

    ScpPattern(String engNormalized) {
        this.engNormalized = engNormalized;
    }
}
