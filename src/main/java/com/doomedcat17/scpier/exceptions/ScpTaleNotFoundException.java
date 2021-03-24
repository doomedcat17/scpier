package com.doomedcat17.scpier.exceptions;

public class ScpTaleNotFoundException extends Exception {

    public ScpTaleNotFoundException(String message) {
        super("Could not find scpTale: "+message);
    }
}
