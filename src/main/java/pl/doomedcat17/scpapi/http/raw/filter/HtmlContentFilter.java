package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.HashMap;

public interface HtmlContentFilter {

    HashMap<String, String> filterHtmlContent(String content);
}
