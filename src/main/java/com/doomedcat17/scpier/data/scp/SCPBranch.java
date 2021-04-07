package com.doomedcat17.scpier.data.scp;

public enum SCPBranch {
    ENGLISH("eng", "http://www.scpwiki.com/", SCPIdentifierPlacement.NONE),
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
    CZECH("cs", "http://scp-cs.wikidot.com/", SCPIdentifierPlacement.ENDING);

    public String identifier;

    public String url;

    public SCPIdentifierPlacement scpIdentifierPlacement;

    SCPBranch(String identifier, String url, SCPIdentifierPlacement scpIdentifierPlacement) {
        this.identifier = identifier;
        this.url = url;
        this.scpIdentifierPlacement = scpIdentifierPlacement;
    }
}


