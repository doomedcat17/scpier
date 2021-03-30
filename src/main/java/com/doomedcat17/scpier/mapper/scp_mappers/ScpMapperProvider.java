package com.doomedcat17.scpier.mapper.scp_mappers;


public class ScpMapperProvider {


    public static ScpMapper getScpMapper(String scpName, boolean mapped) {
        switch (scpName) {
            default:
                if (mapped) return new DefaultMappedScpMapper();
                else return new DefaultUnmappedScpMapper();
        }
    }
}
