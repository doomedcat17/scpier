package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

public enum ScpPattern {
    OBJECT_NAME("Item #"),
    OBJECT_CLASS("Object Class"),
    CONTAINMENT_CLASS("Containment Class"),
    OBJECT_SECONDARY_CLASS("Secondary Class"),
    OBJECT_DISRUPT_CLASS("Disruption Class"),
    OBJECT_RISK_CLASS("Risk Class"),
    OBJECT_PROCEDURES("Special Containment Procedures"),
    DESCRIPTION("Description"),
    SCP_ADDENDUM("Addendum"),
    SCP_DOCUMENT("Document"),
    SCP_RECOVERY("Recovery Log"),
    SCP_TEST_LOG("Test log"),
    SCP_INCIDENT("Incident"),
    SCP_EXPERIMENT("Experiment Log"),
    SCP_REPORT("Report"),
    SCP_DISCOVERY("Discovery");

    public String engNormalized;

    ScpPattern(String engNormalized) {
        this.engNormalized = engNormalized;
    }

    public static boolean containsValue(String text, String language) {
        if (language.equals("eng")) {
            for (ScpPattern scpPattern: ScpPattern.values()) {
                if (text.contains(scpPattern.engNormalized)) return true;
            }
        }
        return false;
    }
}
