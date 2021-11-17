package com.doomedcat17.scpier.page.webclients;

import com.gargoylesoftware.htmlunit.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RateLimitedWebClient extends WebClient {

    private long requestCounter = 0;

    private LocalTime startTime;

    private LocalTime endTime;

    private final long TIME_PERIOD;

    private final long REQUEST_CAP;

    @Override
    public <P extends Page> P getPage(String url) throws IOException, FailingHttpStatusCodeException {
        if (startTime == null) startTime = LocalTime.now();
        if (endTime == null) endTime = startTime.plusSeconds(TIME_PERIOD);
        boolean reachedCap = LocalTime.now().isBefore(endTime) && requestCounter == REQUEST_CAP;
        while (reachedCap) {
            reachedCap = LocalTime.now().isBefore(endTime) && requestCounter == REQUEST_CAP;
        }
        if (LocalTime.now().isAfter(endTime)) {
            startTime = LocalTime.now();
            endTime = startTime.plusSeconds(TIME_PERIOD);
            requestCounter = 0;
        }
        requestCounter++;
        return super.getPage(url);
    }

    public RateLimitedWebClient(long timePeriod, long requestCap) {
        super(BrowserVersion.CHROME);
        TIME_PERIOD = timePeriod;
        REQUEST_CAP = requestCap;
        super.getOptions().setJavaScriptEnabled(true);
        super.getOptions().setThrowExceptionOnScriptError(false);
        super.setAjaxController(new NicelyResynchronizingAjaxController());
        super.getOptions().setCssEnabled(false);
        super.setCssErrorHandler(new SilentCssErrorHandler());
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

    }
}
