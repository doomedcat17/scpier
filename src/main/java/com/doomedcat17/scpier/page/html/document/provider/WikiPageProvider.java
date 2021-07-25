package com.doomedcat17.scpier.page.html.document.provider;

import com.doomedcat17.scpier.page.WikiContent;

import java.io.IOException;

public interface WikiPageProvider {

    WikiContent getWebpageContent(String url) throws IOException;
}
