package pl.doomedcat17.scpapi.raw.content;

import java.util.HashMap;

public interface HtmlContentFilter {

    HashMap<String, String> filterHtmlContent(String content);
}
