package com.doomedcat17.scpier.data.scp;

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
    CZECH("cs","http://scp-cs.wikidot.com/");

    public String identifier;
    public String url;

    SCPTranslation(String identifier, String url) {
        this.identifier = identifier;
        this.url = url;
    }
}
