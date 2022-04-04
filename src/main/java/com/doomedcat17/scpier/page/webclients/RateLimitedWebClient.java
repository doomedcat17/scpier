package com.doomedcat17.scpier.page.webclients;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.javascript.SilentJavaScriptErrorListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;

public class RateLimitedWebClient extends WebClient {

    private long requestCounter = 0;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private final long TIME_PERIOD;

    private final long REQUEST_CAP;

    @Override
    public <P extends Page> P getPage(String url) throws IOException {
        try {
            if (startTime == null) startTime = LocalDateTime.now();
            if (endTime == null) endTime = startTime.plusSeconds(TIME_PERIOD);
            boolean reachedCap = LocalDateTime.now().isBefore(endTime) && requestCounter == REQUEST_CAP;
            while (reachedCap) {
                reachedCap = LocalDateTime.now().isBefore(endTime) && requestCounter == REQUEST_CAP;
            }
            if (LocalDateTime.now().isAfter(endTime)) {
                startTime = LocalDateTime.now();
                endTime = startTime.plusSeconds(TIME_PERIOD);
                requestCounter = 0;
            }
            requestCounter++;
            super.getCache().clear();
            return super.getPage(url);
        } catch (FailingHttpStatusCodeException e) {
            throw new IOException();
        }
    }

    public RateLimitedWebClient(long timePeriod, long requestCap) {
        super(BrowserVersion.CHROME);
        this.TIME_PERIOD = timePeriod;
        this.REQUEST_CAP = requestCap;
        configureOptions();
        super.setFrameContentHandler(baseFrameElement -> false);
        super.getCache().setMaxSize(0);
        super.getCache().clear();
        super.setCssErrorHandler(new SilentCssErrorHandler());
        super.setJavaScriptErrorListener(new SilentJavaScriptErrorListener());
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

    }

    private void configureOptions() {
        super.getOptions().setJavaScriptEnabled(true);
        super.getOptions().setThrowExceptionOnScriptError(false);
        super.getOptions().setPrintContentOnFailingStatusCode(false);
        super.setAjaxController(new NicelyResynchronizingAjaxController());
        super.getOptions().setCssEnabled(false);
        super.getOptions().setAppletEnabled(false);
        super.getOptions().setDownloadImages(false);
        super.getOptions().setFetchPolyfillEnabled(true);
    }
}
