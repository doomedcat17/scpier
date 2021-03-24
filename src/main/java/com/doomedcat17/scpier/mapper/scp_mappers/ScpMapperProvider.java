package com.doomedcat17.scpier.mapper.scp_mappers;


public class ScpMapperProvider {


    public static ScpMapper getScpMapper(String scpName) {
        switch (scpName) {
            default:
                return new DefaultScpMapper();
        }
    }
}
