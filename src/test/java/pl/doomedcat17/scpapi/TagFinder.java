package pl.doomedcat17.scpapi;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContentProvider;

import java.io.IOException;
import java.util.Scanner;

public class TagFinder {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String tag = scanner.nextLine();
        PageContentProvider pageContentProvider = new PageContentProvider();
        for (int i = 2; i <= 5000; i++) {
            StringBuilder scpNumber = new StringBuilder(String.valueOf(i));
            while (scpNumber.length() < 3) {
                scpNumber.insert(0, "0");
            }
            PageContent pageContent = pageContentProvider.getPageContent("http://www.scpwiki.com/scp-"+scpNumber.toString());
            Element element = pageContent.getContent();
            if (element.getAllElements().is(tag)) {
                for (Element tagElement: element.select(tag)) {
                    System.out.println("SCP-"+scpNumber+'\n');
                    System.out.println(tagElement.html());
                }
            }
        }
    }
}
