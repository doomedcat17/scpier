package com.doomedcat17.scpier.data.scp;

import java.util.Arrays;
import java.util.Optional;

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
    DANISH("nd","http://scp-nd.wikidot.com/da:", SCPIdentifierPlacement.ENDING),
    FINNISH("nd","http://scp-nd.wikidot.com/fo:", SCPIdentifierPlacement.ENDING),
    NORWEGIAN("nd","http://scp-nd.wikidot.com/no:", SCPIdentifierPlacement.ENDING),
    SWEDISH("nd","http://scp-nd.wikidot.com/sv:", SCPIdentifierPlacement.ENDING),
    TURKISH("tr","http://scpvakfi.wikidot.com/", SCPIdentifierPlacement.MIDDLE),
    VIETNAMESE("vn","http://scp-vn.wikidot.com/", SCPIdentifierPlacement.ENDING);

    public String identifier;

    public String url;

    public SCPIdentifierPlacement scpIdentifierPlacement;

    SCPBranch(String identifier, String url, SCPIdentifierPlacement scpIdentifierPlacement) {
        this.identifier = identifier;
        this.url = url;
        this.scpIdentifierPlacement = scpIdentifierPlacement;
    }

    public static SCPBranch getByIdentifier(String identifier) {
        Optional<SCPBranch> foundScpBranch = Arrays.stream(SCPBranch.values())
                .filter(scpBranch -> scpBranch.identifier.equals(identifier)).findFirst();
        if (foundScpBranch.isPresent()) return foundScpBranch.get();
        else throw new NullPointerException();
    }
}


