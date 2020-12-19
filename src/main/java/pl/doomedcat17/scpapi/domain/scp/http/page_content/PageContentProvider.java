package pl.doomedcat17.scpapi.domain.scp.http.page_content;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProvider;
import pl.doomedcat17.scpapi.domain.scp.http.HTMLDocumentProviderImpl;
import java.io.IOException;

public class PageContentProvider {
    //TODO handle IO exception
    public PageContent getPageContent(String url) throws IOException {
        HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();
        Document pageContent = htmlDocumentProvider.getWebpageContent(url);
        Element content = pageContent.getElementById("page-content");
        clearContent(content);
        return new PageContent(content, url);
    }

    private void clearContent(Element content) {
        Elements footersElements = content.select(".footer-wikiwalk-nav");
        if (!footersElements.isEmpty()) {
            footersElements.forEach(Node::remove);
        }
        Elements rateWidgetBoxes = content.select(".page-rate-widget-box");
        if (!rateWidgetBoxes.isEmpty()) {
            rateWidgetBoxes.forEach(Node::remove);
        }
        Elements licenseBoxes = content.select(".licensebox22");
        if (!licenseBoxes.isEmpty()) {
            licenseBoxes.forEach(Node::remove);
        }
        content.children().forEach(element -> {
            if (element.childrenSize() == 0 && element.text().equals("")) {
                element.remove();
            }
        });
    }


}
