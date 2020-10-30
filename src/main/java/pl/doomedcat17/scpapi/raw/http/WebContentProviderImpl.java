package pl.doomedcat17.scpapi.raw.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebContentProviderImpl implements WebContentProvider {


    @Override
    public String getContent(String url) {
        String content = "";
        try {
            Connection conn = Jsoup.connect(url);
            Document doc = conn.get();
            content = doc.body().html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
