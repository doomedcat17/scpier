package com.doomedcat17.scpier.exception.page.html.document.preset;

public class PresetExecutorException extends Exception {
    public PresetExecutorException(Throwable cause) {
        super("Could not execute preset", cause);
    }
}
