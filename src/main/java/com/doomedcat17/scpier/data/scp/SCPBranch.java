package com.doomedcat17.scpier.data.scp;

import com.doomedcat17.scpier.exception.data.ScpBranchNotFound;

import java.util.Arrays;

@SuppressWarnings("HttpUrlsUsage")
public enum SCPBranch {
    ENGLISH("eng", "http://www.scp-wiki.wikidot.com/", SCPIdentifierPlacement.NONE),
    POLISH("pl", "http://scp-pl.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    RUSSIAN("ru", "http://scp-ru.wikidot.com/", SCPIdentifierPlacement.ENDING),
    JAPANESE("jp", "http://scp-jp.wikidot.com/", SCPIdentifierPlacement.ENDING),
    CHINESE("cn", "http://scp-wiki-cn.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    KOREAN("ko", "http://ko.scp-wiki.net/", SCPIdentifierPlacement.ENDING),
    FRENCH("fr", "http://fondationscp.wikidot.com/", SCPIdentifierPlacement.ENDING),
    SPANISH("es", "http://lafundacionscp.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    THAI("th", "http://scp-th.wikidot.com/", SCPIdentifierPlacement.ENDING),
    GERMAN("de", "http://scp-wiki-de.wikidot.com/", SCPIdentifierPlacement.ENDING),
    ITALIAN("it", "http://fondazionescp.wikidot.com/", SCPIdentifierPlacement.ENDING),
    UKRAINIAN("ua", "http://scp-ukrainian.wikidot.com/", SCPIdentifierPlacement.ENDING),
    PORTUGUESE("pt", "http://scp-pt-br.wikidot.com/", SCPIdentifierPlacement.ENDING),
    CZECH("cs", "http://scp-cs.wikidot.com/", SCPIdentifierPlacement.ENDING),
    GREEK("el","http://scp-el.wikidot.com/", SCPIdentifierPlacement.ENDING),
    INDONESIAN("idn","http://scp-idn.wikidot.com/", SCPIdentifierPlacement.ENDING),
    NORDIC("nd","http://scp-nd.wikidot.com/", SCPIdentifierPlacement.ENDING),
    TURKISH("tr","http://scpvakfi.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    VIETNAMESE("vn","http://scp-vn.wikidot.com/", SCPIdentifierPlacement.ENDING),
    CHINESE_TRADITIONAL("zh","http://scp-zh-tr.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    ARABIAN("ar","http://scp-ar.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    ESTONIAN("et", "http://scp-et.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    HUNGARIAN("hu", "http://scp-hu.wikidot.com/", SCPIdentifierPlacement.ENDING),
    ROMANIAN("ro", "http://scp-ro.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    SLOVENIAN("sl", "http://scp-slovenija.wikidot.com/", SCPIdentifierPlacement.ENDING);

    public final String identifier;

    public final String url;

    public final SCPIdentifierPlacement scpIdentifierPlacement;

    SCPBranch(String identifier, String url, SCPIdentifierPlacement scpIdentifierPlacement) {
        this.identifier = identifier;
        this.url = url;
        this.scpIdentifierPlacement = scpIdentifierPlacement;
    }

    public static SCPBranch getByUrl(String url) {
        if (url.equals("http://scp-int.wikidot.com/")) return SCPBranch.ENGLISH;
        return Arrays.stream(SCPBranch.values())
                .filter(scpBranch -> scpBranch.url.equals(url))
                .findFirst().orElse(null);
    }

    public static SCPBranch getById(String branchId) {
        return Arrays.stream(SCPBranch.values())
                .filter(scpBranch -> scpBranch.identifier.equals(branchId))
                .findFirst().orElseThrow(() -> new ScpBranchNotFound(branchId));
    }
}


