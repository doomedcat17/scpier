package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProvider;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProviderImpl;
import java.io.IOException;

public class PageContentProvider {

    private final String[] elementsToRemove = {".footer-wikiwalk-nav", ".page-rate-widget-box", ".licensebox22", ".creditRate", "#u-credit-view", ".info-container"};
    //TODO handle IO exception
    public PageContent getPageContent(String url) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();
        Document pageContent = htmlDocumentProvider.getWebpageContent(url);
        Element content = pageContent.getElementById("page-content");
        clearContent(content);
        return new PageContent(content, url);
    }

    private void clearContent(Element content) {
        for (String name: elementsToRemove) {
            Elements elements = content.select(name);
            if (!elements.isEmpty()) {
                elements.forEach(Node::remove);
            }
        }
        content.children().forEach(element -> {
            if (element.childrenSize() == 0 && !element.hasText()) {
                element.remove();
            }
        });
    }


}
