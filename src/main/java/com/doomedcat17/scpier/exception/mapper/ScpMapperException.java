package com.doomedcat17.scpier.exception.mapper;

public class ScpMapperException extends RuntimeException {
    public ScpMapperException(Throwable cause) {
        super("Could not map content", cause);
    }
}
