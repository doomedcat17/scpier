package com.doomedcat17.scpier.exception;

public class SCPierResourcesInitializationException extends RuntimeException {
    public SCPierResourcesInitializationException(Throwable cause) {
        super("Could not initialize resources", cause);
    }


}
