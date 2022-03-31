package com.doomedcat17.scpier.exception.data;

public class ScpLanguageNotFound extends RuntimeException {
    public ScpLanguageNotFound(String langId) {
        super("Language not found: "+langId);
    }
}
