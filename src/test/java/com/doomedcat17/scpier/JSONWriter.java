package com.doomedcat17.scpier;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import com.doomedcat17.scpier.data.scp.ScpObject;
import com.doomedcat17.scpier.data.scp.ScpTale;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONWriter {

    public static void main(String[] args) throws JsonProcessingException {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        try {
            ScpObject scp = scpFoundationDataProvider.getScpObject("2030", SCPBranch.ENGLISH, SCPTranslation.ORIGINAL);
            ScpTale scpTale = scpFoundationDataProvider.getScpTale("sympathy-for-an-empath", SCPBranch.ENGLISH, SCPTranslation.ORIGINAL);
            // ScpObject scpObject = scpFoundationDataProvider.getFirstScpObject("qntm-s-proposal", SCPBranch.ENGLISH, SCPTranslation.POLISH, false);
            System.out.println(asJSONString(scp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String asJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }


}
