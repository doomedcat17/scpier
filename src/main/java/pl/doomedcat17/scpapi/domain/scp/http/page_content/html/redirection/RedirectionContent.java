package pl.doomedcat17.scpapi.domain.scp.http.page_content.html.redirection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentProvider;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.html.HTMLDocumentProviderImpl;

import java.io.IOException;
import java.util.List;

public class RedirectionContent {

    HTMLDocumentProvider htmlDocumentProvider = new HTMLDocumentProviderImpl();

    public List<Node> getRedirectionContent(String type, Element element) throws IOException {
        if ("u-adult-warning".equals(type)) {
            Element linkElement = element.getElementsByTag("a").get(0);
            String redirectionLink = linkElement.attr("href");
            Document document = htmlDocumentProvider.getWebpageContent(redirectionLink);
            return document.getElementById("page-content").childNodesCopy();
        }
        throw new NullPointerException();

    }
}
