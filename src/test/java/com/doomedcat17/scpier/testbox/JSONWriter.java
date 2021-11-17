package com.doomedcat17.scpier.testbox;

import com.doomedcat17.scpier.ScpFoundationDataProvider;
import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPLanguage;
import com.doomedcat17.scpier.data.scp.ScpWikiData;
import com.doomedcat17.scpier.page.webclients.NicelyLimitedWebClient;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.time.LocalTime;
import java.util.Date;

public class JSONWriter {
    static NicelyLimitedWebClient nicelyLimitedWebClient = new NicelyLimitedWebClient();
    public static void main(String[] args) throws JsonProcessingException {
        ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ScpWikiData scp = scpFoundationDataProvider.getScpWikiData("scp-039", SCPBranch.ENGLISH);
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(scp));

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
