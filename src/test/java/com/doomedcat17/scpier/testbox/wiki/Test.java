package com.doomedcat17.scpier.testbox.wiki;

import java.util.Set;

public class Test {

    public static void main(String[] args) {
        Set<Article> titles = RecentChangesTitleFinder.getAllTitles("http://ko.scp-wiki.net/");
        System.out.println("Total size: "+titles.size());
    }
}
