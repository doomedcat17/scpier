package com.doomedcat17.scpier.exception.scraper;

public class ElementScraperException extends RuntimeException {

    public ElementScraperException(Throwable cause) {
        super("Content scraping error", cause);
    }
}
