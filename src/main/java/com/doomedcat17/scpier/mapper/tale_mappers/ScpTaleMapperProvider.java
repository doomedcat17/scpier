package com.doomedcat17.scpier.mapper.tale_mappers;

import com.doomedcat17.scpier.mapper.scp_mappers.DefaultScpMapper;

public class ScpTaleMapperProvider {

    public static ScpTaleMapper getScpTaleMapper(String taleName) {
        switch (taleName) {
            default:
                return new DefaultScpTaleMapper();
        }
    }
}
