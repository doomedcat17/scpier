package pl.doomedcat17.scpapi.domain.scp.http.page_content.html;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HTMLDocumentProviderImpl implements HTMLDocumentProvider {

    public Document getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        return conn.get();
    }
}
