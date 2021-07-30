package com.doomedcat17.scpier.exception.page.html.document.cleaner;

public class WikiContentCleanerException extends RuntimeException {
    public WikiContentCleanerException(Throwable cause) {
        super("Content cleanup error", cause);
    }
}
