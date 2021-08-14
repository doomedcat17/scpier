package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp.SCPTranslation;

import java.util.Locale;

public class WikiSourceBuilder {

    public static String buildSource(String articleName, SCPBranch scpBranch, SCPTranslation scpTranslation) {
        StringBuilder sourceBuilder = new StringBuilder(articleName.toLowerCase(Locale.ROOT));
        if (articleName.startsWith("scp") && (scpBranch != SCPBranch.ENGLISH && !articleName.contains(scpBranch.identifier))) {
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
