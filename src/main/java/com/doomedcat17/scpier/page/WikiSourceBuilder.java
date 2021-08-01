package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp.SCPTranslation;

import java.util.Locale;

public class WikiSourceBuilder {

    public static String buildSource(String objectName, SCPBranch scpBranch, SCPTranslation scpTranslation) {
        StringBuilder sourceBuilder = new StringBuilder(objectName.toLowerCase(Locale.ROOT));
        if (objectName.startsWith("scp") && (scpBranch != SCPBranch.ENGLISH && !objectName.contains(scpBranch.identifier))) {
            if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.ENDING) {
                sourceBuilder
                        .append("-")
                        .append(scpBranch.identifier);
            } else if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.MIDDLE) {
                sourceBuilder
                        .replace(3, 3, "-"+scpBranch.identifier);
            }
        }
        if (scpTranslation.url.equals("")) {
            sourceBuilder.insert(0, scpBranch.url);
        } else sourceBuilder.insert(0, scpTranslation.url);
        return sourceBuilder.toString();
    }
}
