package com.doomedcat17.scpier.testbox.wiki;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.data.scp.SCPTranslation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;

public class TitleFinder {

    public static void findAllTitles(Document document, Set<Article> tales, String src) throws IOException {
        Element content = document.selectFirst("#page-content");
        Elements elements = content.select("a");
        elements.stream().filter(element -> element.attr("href").startsWith("/"))
                .forEach(element -> {
                    String title = element.attr("href").substring(1);
                    if (!isTrash(title)) {
                        Article article = new Article();
                        article.setSource(src + title);
                        for (SCPTranslation scpTranslation : SCPTranslation.values()) {
                            if (title.startsWith(scpTranslation.identifier + ":") || title.startsWith("adult:")) {
                                title = title.substring(title.indexOf(':') + 1);
                                break;
                            }
                        }
                        article.setName(title);
                        tales.add(article);
                    }
                });
    }

    //TODO all system:page-tags/tag/author#pages
    private static boolean isTrash(String title) {
        return title.startsWith("author") || title.startsWith("theme") || title.startsWith("component") || title.startsWith("fragment:") || title.contains("new-pages-feed")
                || title.contains("most-recently-created") || title.startsWith("forum") || title.startsWith("admin") || title.equals("floppyphoenix") || title.equals("scp-style-resource")
                || title.equals("anomaly-classification-system-guide") || title.startsWith("system:") || title.startsWith("nav:") || title.startsWith("search:") || title.equals("sandbox");
    }

    public static void findTitles(SCPBranch scpBranch, Set<Article> titles) {
        String url = "most-recently-created/p/";
        if (scpBranch.equals(SCPBranch.POLISH)) url = "ostatnio-stworzone/p/";
        if (scpBranch.equals(SCPBranch.SPANISH)) url = "recientemente-creados/p/";
        if (scpBranch.equals(SCPBranch.INDONESIAN)) url = "new-pages-feed/p/";
        if (scpBranch.equals(SCPBranch.ARABIAN)) url = "new-pages-feed/untagged_p/";
        if (scpBranch.equals(SCPBranch.HUNGARIAN)) url = "new-pages-feed/scps_p/";
        if (scpBranch.equals(SCPBranch.TURKISH)) url = "en-son-yaratilan/p/";
        if (scpBranch.equals(SCPBranch.CHINESE_TRADITIONAL)) url = "system:recent-changes/p/";
        if (scpBranch.equals(SCPBranch.ITALIAN) || scpBranch.equals(SCPBranch.SLOVENIAN) || scpBranch.equals(SCPBranch.ESTONIAN) || scpBranch.equals(SCPBranch.KOREAN)) {
            titles.addAll(RecentChangesTitleFinder.getAllTitles(scpBranch.url));
        } else {
            int lastSize = titles.size();
            for (int i = 1; i <= 750; i++) {
                try {
                    Document wikiPage = Jsoup.connect(scpBranch.url + url + i).get();
                    findAllTitles(wikiPage, titles, scpBranch.url);
                    if (lastSize == titles.size()) break;
                    lastSize = titles.size();
                } catch (IOException e) {
                    break;
                }
            }
        }
    }
}
