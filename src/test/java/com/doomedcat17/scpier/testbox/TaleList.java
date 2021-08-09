package com.doomedcat17.scpier.testbox;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TaleList {

    public static void main(String[] args) throws IOException {
        Set<String> tales = new HashSet<>();
        Document document = Jsoup.connect("https://scp-wiki.wikidot.com/tales-by-title").get();
        Element content = document.selectFirst("#page-content");
        Elements elements = content.select("a");
        elements.stream().filter(element -> element.attr("href").startsWith("/"))
                .forEach(element -> tales.add(element.attr("href").substring(1)));

        tales.forEach(System.out::println);
    }
}
