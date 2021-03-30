package com.doomedcat17.scpier.mapper.tale_mappers;

public class ScpTaleMapperProvider {

    public static ScpTaleMapper getScpTaleMapper(String taleName) {
        switch (taleName) {
            default:
                return new DefaultScpTaleMapper();
        }
    }
}
