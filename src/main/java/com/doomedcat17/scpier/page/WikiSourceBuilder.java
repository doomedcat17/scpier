package com.doomedcat17.scpier.page;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPIdentifierPlacement;
import com.doomedcat17.scpier.data.scp.SCPLanguage;

import java.util.Locale;

public class WikiSourceBuilder {

    public static String buildSource(String articleName, SCPBranch scpBranch, SCPLanguage scpLanguage) {
        if (isValidForLocalization(articleName, scpBranch)) {
            articleName = localizeScpName(articleName, scpBranch);
        }
        String url;
        if (scpLanguage.identifier.equals(scpBranch.identifier)) url = scpBranch.url;
        else url = scpLanguage.url;
        return url + articleName;
    }

    public static String buildSource(String articleName, SCPBranch scpBranch) {
        if (isValidForLocalization(articleName, scpBranch)) {
            articleName = localizeScpName(articleName, scpBranch);
        }
        return scpBranch.url + articleName;
    }

    private static boolean isValidForLocalization(String articleName, SCPBranch scpBranch) {
        return articleName.startsWith("scp") && (scpBranch != SCPBranch.ENGLISH && !articleName.contains(scpBranch.identifier));
    }

    private static String localizeScpName(String articleName, SCPBranch scpBranch) {
        StringBuilder articleNameBuilder = new StringBuilder(articleName.toLowerCase(Locale.ENGLISH));
        if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.ENDING) {
            articleNameBuilder
                    .append("-")
                    .append(scpBranch.identifier);
        } else if (scpBranch.scpIdentifierPlacement == SCPIdentifierPlacement.MIDDLE) {
            articleNameBuilder
                    .replace(3, 3, "-" + scpBranch.identifier);
        }
        return articleNameBuilder.toString();
    }
}
