package com.doomedcat17.scpier;

import com.doomedcat17.scpier.exceptions.ScpObjectNotFoundException;
import com.doomedcat17.scpier.pagecontent.PageContent;
import com.doomedcat17.scpier.pagecontent.PageContentProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws ScpObjectNotFoundException, IOException {
        PageContentProvider pageContentProvider = new PageContentProvider();
        PageContent pageContent =
                pageContentProvider.getPageContent("http://www.scpwiki.com/scp-4786");
        File file = new File("src/test/resources/html/output/output.html");
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(pageContent.getContent().html());
        myWriter.close();
    }
}
