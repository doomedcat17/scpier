package pl.doomedcat17.scpapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

public class TestDataProvider {

    private static Document sampleScpDocument;


    public static Element getSamplePageContent() {
        if (sampleScpDocument == null) {
            try {
                sampleScpDocument = Jsoup.parse(
                        new File("src/test/resources/html/scp1.html"),
                        "UTF-8",
                        ""
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sampleScpDocument.getElementById("page-content");
    }
}
