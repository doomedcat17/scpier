package pl.doomedcat17.scpapi.data.scp;

public enum ScpPattern {
    OBJECT_NAME("Item #"),
    OBJECT_ALTERNATIVE_NAME("Item"),
    OBJECT_SECOND_ALTERNATIVE_NAME("Item#"),
    OBJECT_THIRD_ALTERNATIVE_NAME("Object #"),
    OBJECT_CLASS("Object Class"),
    OBJECT_ALTERNATIVE_CLASS("Object class"),
    OBJECT_SECOND_ALTERNATIVE_CLASS("Item Class"),
    OBJECT_THIRD_ALTERNATIVE_CLASS("Classification"),
    OBJECT_FOURTH_ALTERNATIVE_CLASS("Anomaly Class"),
    OBJECT_FIFTH_ALTERNATIVE_CLASS("Obj3ct Class"),
    OBJECT_SIXTH_ALTERNATIVE_CLASS("Object Class Class"),
    CONTAINMENT_CLASS("Containment Class"),
    OBJECT_SECONDARY_CLASS("Secondary Class"),
    OBJECT_DISRUPT_CLASS("Disruption Class"),
    OBJECT_RISK_CLASS("Risk Class"),
    OBJECT_PROCEDURES("Special Containment Procedures"),
    DESCRIPTION("Description"),
    SCP_ADDENDUM("Addendum"),
    SCP_APPENDIX("Appendix"),
    SCP_DOCUMENT("Document"),
    SCP_RECOVERY("Recovery Log"),
    SCP_TEST_LOG("Test log"),
    SCP_INCIDENT("Incident"),
    SCP_EXPERIMENT_LOGS("Experiment Log"),
    SCP_EXPERIMENT_LOG("Experiment Logs"),
    SCP_REPORT("Report"),
    SCP_CREATOR_INFO("Creator Information"),
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

    //TODO dodać exceptions
    public static boolean isItemName(String text, String language) {
        if (language.equals("eng")) {
            return text.contains("Item") || text.equals(OBJECT_THIRD_ALTERNATIVE_NAME.engNormalized);
        }
        return false;
    }

    public static boolean isObjectClass(String text, String language) {
        if (language.equals("eng")) {
            return text.equals(ScpPattern.OBJECT_CLASS.engNormalized)
                    || text.equals(ScpPattern.CONTAINMENT_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_ALTERNATIVE_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_SECOND_ALTERNATIVE_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_THIRD_ALTERNATIVE_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_FOURTH_ALTERNATIVE_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_FIFTH_ALTERNATIVE_CLASS.engNormalized)
                    || text.equals(ScpPattern.OBJECT_SIXTH_ALTERNATIVE_CLASS.engNormalized);
        }
        return false;
    }
}
