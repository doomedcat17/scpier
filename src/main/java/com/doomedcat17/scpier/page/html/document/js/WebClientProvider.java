package com.doomedcat17.scpier.page.html.document.js;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

import java.util.logging.Level;

public class WebClientProvider {

    private static WebClient webClient;

    public static synchronized WebClient getWebClient() {
        if(webClient == null){
            webClient = new WebClient(BrowserVersion.CHROME);
            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
        }
        return webClient;


    }
}
