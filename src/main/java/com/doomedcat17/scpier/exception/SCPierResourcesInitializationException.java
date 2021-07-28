package com.doomedcat17.scpier.exception;

public class SCPierResourcesInitializationException extends RuntimeException {
    public SCPierResourcesInitializationException(String message) {
        super("Could not initialize resources: "+message);
    }
}
