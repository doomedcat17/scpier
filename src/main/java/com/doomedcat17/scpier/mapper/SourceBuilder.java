package com.doomedcat17.scpier.mapper;

import com.doomedcat17.scpier.data.scp_object.SCPBranch;
import com.doomedcat17.scpier.data.scp_object.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp_object.SCPTranslation;
import org.jsoup.internal.StringUtil;

public class SourceBuilder {

    public String buildSource(String objectName, SCPBranch scpBranch, SCPTranslation scpTranslation) {
        StringBuilder sourceBuilder = new StringBuilder(objectName);
        if (StringUtil.isNumeric(objectName)) {
            while (sourceBuilder.length() < 3) sourceBuilder.insert(0, "0");
            if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.ENDING) {
                sourceBuilder
                        .append("-")
                        .append(scpBranch.identifier);
            } else if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.MIDDLE) {
                sourceBuilder
                        .insert(0, "-")
                        .insert(0, scpBranch.identifier);
            }
            sourceBuilder
                    .insert(0, "scp-");
        }
        if (scpTranslation.url.equals("")) {
            sourceBuilder.insert(0, scpBranch.url);
        } else sourceBuilder.insert(0, scpTranslation.url);
        return sourceBuilder.toString();
    }
}
