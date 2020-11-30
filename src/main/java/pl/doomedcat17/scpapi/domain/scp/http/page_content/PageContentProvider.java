package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProvider;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProviderImpl;
import java.io.IOException;

public class PageContentProvider {
    //TODO handle IO exception
    public PageContent getPageContent(String url) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();
        Document pageContent = htmlDocumentProvider.getWebpageContent(url);
        Element content = pageContent.getElementById("page-content");
        return new PageContent(content, url);
    }


}
