package com.doomedcat17.scpier.page.html.document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

import java.util.logging.Level;

public class WebClientProvider {


    public static synchronized WebClient getWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        return webClient;


    }
}
