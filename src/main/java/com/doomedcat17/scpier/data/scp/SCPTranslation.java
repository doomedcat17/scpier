package com.doomedcat17.scpier.data.scp;

import java.util.Arrays;
import java.util.Optional;

public enum SCPTranslation {
    ORIGINAL("", ""),
    ENGLISH("eng", "http://scp-int.wikidot.com/"),
    POLISH("pl", "http://scp-wiki.net.pl/"),
    RUSSIAN("ru", "http://scp-ru.wikidot.com/"),
    JAPANESE("jp", "http://scp-jp.wikidot.com/"),
    CHINESE("cn", "http://scp-wiki-cn.wikidot.com/"),
    KOREAN("ko","http://ko.scp-wiki.net/"),
    FRENCH("fr","http://fondationscp.wikidot.com/"),
    SPANISH("es","http://lafundacionscp.wikidot.com/"),
    THAI("th","http://scp-th.wikidot.com/"),
    GERMAN("de","http://scp-wiki-de.wikidot.com/"),
    ITALIAN("it","http://fondazionescp.wikidot.com/"),
    UKRAINIAN("ua","http://scp-ukrainian.wikidot.com/"),
    PORTUGUESE("pt","http://scp-pt-br.wikidot.com/"),
    CZECH("cs","http://scp-cs.wikidot.com/"),
    GREEK("el","http://scp-el.wikidot.com/"),
    INDONESIAN("idn","http://scp-idn.wikidot.com/"),
    DANISH("nd","http://scp-nd.wikidot.com/da:"),
    FINNISH("nd","http://scp-nd.wikidot.com/fo:"),
    NORWEGIAN("nd","http://scp-nd.wikidot.com/no:"),
    SWEDISH("nd","http://scp-nd.wikidot.com/sv:"),
    TURKISH("tr","http://scpvakfi.wikidot.com/"),
    VIETNAMESE("vn","http://scp-vn.wikidot.com/"),
    CHINESE_TRADITIONAL("zh","http://scp-zh-tr.wikidot.com/");


    public String identifier;
    public String url;

    SCPTranslation(String identifier, String url) {
        this.identifier = identifier;
        this.url = url;
    }

    //ignores article name
    public static SCPTranslation getByUrl(String url) {
        if (url.contains("http://www.scp-wiki.wikidot.com/")) return SCPTranslation.ENGLISH;
        Optional<SCPTranslation> foundScpTranslation = Arrays.stream(SCPTranslation.values())
                .filter(scpTranslation -> !scpTranslation.url.isEmpty())
                .filter(scpTranslation -> url.contains(scpTranslation.url)).findFirst();
        if (foundScpTranslation.isPresent()) return foundScpTranslation.get();
        else throw new NullPointerException();
    }

    public static SCPTranslation getByIdentifier(String identifier) {
        Optional<SCPTranslation> foundScpBranch = Arrays.stream(SCPTranslation.values())
                .filter(scpTranslation -> scpTranslation.identifier.equals(identifier)).findFirst();
        if (foundScpBranch.isPresent()) return foundScpBranch.get();
        else throw new NullPointerException();
    }
}
