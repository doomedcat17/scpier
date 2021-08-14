package com.doomedcat17.scpier.testbox.wiki;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.testbox.JSONWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AllArticleNamesProvider {

    private static final Set<Article> tales = new HashSet<>();

    public static void main(String[] args) throws IOException {
        for (SCPBranch scpBranch : SCPBranch.values()) {
            System.out.println(scpBranch.toString() + " started");
            if (scpBranch.equals(SCPBranch.ESTONIAN)) continue;
            TitleFinder.findTitles(scpBranch, tales);
            System.out.println(scpBranch + " ended");
            System.out.println("Tales found: "+tales.size());
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/test/resources/all-articles.json"));
        writer.write(JSONWriter.asJSONString(tales));
        writer.close();
    }
}
