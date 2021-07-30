package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.data.files.ResourcesProvider;
import com.doomedcat17.scpier.exception.page.html.document.provider.IframeContentProviderException;
import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.cleaner.DefaultWikiContentCleaner;
import com.doomedcat17.scpier.page.html.document.preset.Preset;
import com.doomedcat17.scpier.page.html.document.provider.IframeContentProvider;
import com.doomedcat17.scpier.page.html.document.provider.ScriptedWikiPageProvider;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;

public class TagFinder {

    public static void main(String[] args) throws IOException, URISyntaxException, IframeContentProviderException {
        String url = "http://www.scpwiki.com/";
        for (int i = 2; i <= 5001; i++) {
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            scpNumber.insert(0, "scp-");
            Connection conn = Jsoup.connect(url+ scpNumber);
            Document document =  conn.get();
            Element content = document.selectFirst("#page-content");
            WikiContent wikiContent = new WikiContent();
            wikiContent.setSourceUrl(url+scpNumber);
            wikiContent.setLangIdentifier("eng");
            wikiContent.setContent(content);
            ResourcesProvider.initResources();
            IframeContentProvider iframeContentProvider = new IframeContentProvider(new ScriptedWikiPageProvider(),
                    new DefaultWikiContentCleaner(ResourcesProvider.getRemovalDefinitions()));
            iframeContentProvider.provideIframesContent(wikiContent, new Preset());
            Elements elements = content.select("p > br");
            if (!elements.isEmpty()) {
                System.out.println(scpNumber);
            }
        }
    }
}
