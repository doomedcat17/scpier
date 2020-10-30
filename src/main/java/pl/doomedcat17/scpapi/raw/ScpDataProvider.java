package pl.doomedcat17.scpapi.raw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.doomedcat17.scpapi.raw.http.WebContentProvider;
import pl.doomedcat17.scpapi.raw.content.HtmlContentFilter;

import java.util.HashMap;

@Component
public class ScpDataProvider  {

    private final String SCP_URL = "http://www.scpwiki.com/scp-";

    private WebContentProvider webContentProvider;

    private HtmlContentFilter htmlContentFilter;

    public HashMap<String, String> getScpData(String scpId) {
        String content = webContentProvider.getContent(SCP_URL+scpId);
        return htmlContentFilter.filterHtmlContent(content);
    }

    @Autowired
    public ScpDataProvider(WebContentProvider webContentProvider, HtmlContentFilter htmlContentFilter) {
        this.webContentProvider = webContentProvider;
        this.htmlContentFilter = htmlContentFilter;
    }
}
