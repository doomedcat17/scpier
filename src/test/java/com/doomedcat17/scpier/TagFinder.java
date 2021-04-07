package com.doomedcat17.scpier;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
public class TagFinder {

    public static void main(String[] args) throws IOException {
        String url = "http://www.scpwiki.com/";
        for (int i = 2; i <= 5001; i++) {
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, '0');
            }
            scpNumber.insert(0, "scp-");
            Connection conn = Jsoup.connect(url+scpNumber.toString());
            Document document =  conn.get();
            Elements elements = document.select("#page-content iframe");
            if (!elements.isEmpty()) {
                System.out.println(scpNumber);
            }
        }
    }
}
