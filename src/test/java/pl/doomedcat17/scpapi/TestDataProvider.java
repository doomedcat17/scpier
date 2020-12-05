package pl.doomedcat17.scpapi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

public class TestDataProvider {

    private static Document sampleScpDocument;

    private static Element sampleLines;

    private static Element loadElementFormHTML(String path) {
        Element element = null;
        try {
            element = Jsoup.parse(
                    new File(path),
                    "UTF-8",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }


    public static Element getSamplePageContent() {
        if (sampleScpDocument == null) {
            try {
                sampleScpDocument = (Document) loadElementFormHTML("src/test/resources/html/scp1.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sampleScpDocument.getElementById("page-content");
    }

    public static Element getSampleLines() {
        if (sampleLines == null) {
            sampleLines = loadElementFormHTML("src/test/resources/html/test_data/SampleLinesElements.html").getElementById("elements");
        }
        return sampleLines;
    }
}

