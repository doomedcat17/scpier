package com.doomedcat17.scpier.page.html.document.redirection;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class WikiRedirectionHandler {

    private final Set<String> redirectionDefinitions;

    private final WikiPageProvider wikiPageProvider;

    public Element getRedirectionContent(Element element, String scpUrl) throws IOException {
        String redirectionLink = element.attr("href");
        if (redirectionLink.startsWith("/")) {
            int charPosition = scpUrl.lastIndexOf('/');
            redirectionLink = scpUrl.substring(0, charPosition) + redirectionLink;
        }
        WikiContent webpageContent = wikiPageProvider.getWebpageContentWithoutRunningJs(redirectionLink);
        return webpageContent.getContent();
    }

    public Optional<Element> provideRedirectedContent(Element content) {
        for (String elementDefinition : redirectionDefinitions) {
            Element foundRedirectionElement = content.selectFirst(elementDefinition);
            if (foundRedirectionElement != null) {
                Element linkElement;
                if (foundRedirectionElement.is("a")) {
                    linkElement = foundRedirectionElement;
                } else linkElement = foundRedirectionElement.selectFirst("a");
                if (linkElement == null) return Optional.empty();
                return Optional.of(linkElement);
            }
        }
        return Optional.empty();
    }

    public WikiRedirectionHandler(WikiPageProvider wikiPageProvider, Set<String> redirectionDefinitions) {
        this.redirectionDefinitions = redirectionDefinitions;
        this.wikiPageProvider = wikiPageProvider;
    }
}
