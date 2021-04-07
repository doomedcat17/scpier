package com.doomedcat17.scpier.pagecontent.html.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HTMLRedirectionHandler {

    private final String REDIRECTION_DEFINITIONS_PATH = "src/main/resources/redirectionElementsDefinitions.json";

    private final List<String> redirectionDefinitions;

    private final HTMLDocumentProvider htmlDocumentProvider;

    public Element getRedirectionContent(Element element, String scpUrl) throws IOException {
        String redirectionLink = element.attr("href");
        if (redirectionLink.startsWith("/")) {
            int charPosition = scpUrl.lastIndexOf('/');
            redirectionLink = scpUrl.substring(0, charPosition) + redirectionLink;
        }
        Document document = htmlDocumentProvider.getWebpageContent(redirectionLink);
        return document.getElementById("page-content");
    }

    public Optional<Element> checkForRedirection(Element content) {
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

    public HTMLRedirectionHandler(HTMLDocumentProvider htmlDocumentProvider) {
        this.htmlDocumentProvider = htmlDocumentProvider;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            redirectionDefinitions =
                    objectMapper.readValue(
                            new File(REDIRECTION_DEFINITIONS_PATH),
                            new TypeReference<>() {
                            });
        } catch (IOException e) {
            throw new RuntimeException("Could not find Redirection Definitions!");
        }
    }
}
