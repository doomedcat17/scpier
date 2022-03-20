package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class JSONWriter {
    public static void main(String[] args) throws JsonProcessingException {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            ScpWikiData scp = scpFoundationDataProvider.getScpWikiData("scp-173", SCPBranch.ENGLISH, SCPLanguage.ENGLISH);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(scp));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
