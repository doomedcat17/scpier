package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import org.jsoup.nodes.Document;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentInterpreter;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentProvider;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentProviderImpl;
import java.io.IOException;

public class PageContentProvider {

    HTMLDocumentInterpreter htmlDocumentInterpreter = new HTMLDocumentInterpreter();

    //TODO handle IO exception
    public PageContent getPageContent(String url) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();
        Document pageDocument = htmlDocumentProvider.getWebpageContent(url);
        PageContent pageContent = new PageContent();
        htmlDocumentInterpreter.mapDocument(pageDocument, pageContent);
        pageContent.setSourceUrl(url);
        return pageContent;
    }

}
