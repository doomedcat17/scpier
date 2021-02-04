package domain.scp.http.page_content.html;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HTMLDocumentProviderImpl implements HTMLDocumentProvider {

    public Document getWebpageContent(String url) throws IOException {
        Connection conn = Jsoup.connect(url);
        return conn.get();
    }
}
