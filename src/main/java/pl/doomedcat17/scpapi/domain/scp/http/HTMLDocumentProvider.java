package pl.doomedcat17.scpapi.domain.scp.http;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface HTMLDocumentProvider {

    Document getWebpageContent(String url) throws IOException;
}