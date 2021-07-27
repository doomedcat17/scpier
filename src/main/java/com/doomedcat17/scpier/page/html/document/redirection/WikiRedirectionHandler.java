package com.doomedcat17.scpier.page.html.document.redirection;

import com.doomedcat17.scpier.page.WikiContent;
import com.doomedcat17.scpier.page.html.document.provider.WikiPageProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class WikiRedirectionHandler {

    private final String REDIRECTION_DEFINITIONS_PATH = "redirectionElementsDefinitions.json";

    private final List<String> redirectionDefinitions;

    private final WikiPageProvider wikiPageProvider;

    public Element getRedirectionContent(Element element, String scpUrl) throws IOException {
        String redirectionLink = element.attr("href");
        if (redirectionLink.startsWith("/")) {
            int charPosition = scpUrl.lastIndexOf('/');
            redirectionLink = scpUrl.substring(0, charPosition) + redirectionLink;
        }
        WikiContent webpageContent = wikiPageProvider.getWebpageContent(redirectionLink);
        return webpageContent.getContent().getElementById("page-content");
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

    public WikiRedirectionHandler(WikiPageProvider wikiPageProvider) {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(REDIRECTION_DEFINITIONS_PATH);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = streamReader.readLine()) != null) jsonBuilder.append(line);
            redirectionDefinitions =
                    objectMapper.readValue(
                            jsonBuilder.toString(),
                            new TypeReference<>() {
                            });
        } catch (IOException e) {
            throw new RuntimeException("Could not find Removal Definitions!");
        }
        this.wikiPageProvider = wikiPageProvider;
    }
}
