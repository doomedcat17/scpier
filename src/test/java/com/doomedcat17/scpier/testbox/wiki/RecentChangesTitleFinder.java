package com.doomedcat17.scpier.testbox.wiki;

import com.doomedcat17.scpier.data.scp.SCPBranch;
import com.doomedcat17.scpier.page.webclients.NicelyLimitedWebClient;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RecentChangesTitleFinder {

    private static final WebClient webClient = new NicelyLimitedWebClient();


    public static Set<Article> getAllTitles(SCPBranch scpBranch) {
        Set<Article> titles = new HashSet<>();
        try {
            HtmlPage recentChangesPage = getPage(scpBranch.url);
            do {
                TitleFinder.findAllTitles(Jsoup.parse(recentChangesPage.asXml()), titles, scpBranch);
                System.out.println(titles.size());
            } while (next(recentChangesPage));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    private static boolean next(HtmlPage page) throws IOException {
        HtmlAnchor nextButton = page.querySelector(".target:last-child > a");
        if (nextButton != null) {
            nextButton.click();
            webClient.waitForBackgroundJavaScript(4500);
            return true;

        } else return false;
    }

    private static HtmlPage getPage(String url) throws IOException {
        String recentChangesUrl = url + "system:recent-changes";
        HtmlPage recentChangesPage = webClient.getPage(recentChangesUrl);
        HtmlCheckBoxInput everythingCheckBox = (HtmlCheckBoxInput) recentChangesPage.getElementById("rev-type-all");
        everythingCheckBox.removeAttribute("checked");
        everythingCheckBox.setChecked(false);
        HtmlCheckBoxInput newPagesCheckbox = (HtmlCheckBoxInput) recentChangesPage.getElementById("rev-type-new");
        newPagesCheckbox.setChecked(true);
        HtmlSelect select = (HtmlSelect) recentChangesPage.getElementById("rev-perpage");
        HtmlOption option = select.getOptionByValue("200");
        select.setSelectedAttribute(option, true);
        HtmlInput refreshButton = recentChangesPage.querySelector(".btn.btn-default.btn-sm");
        refreshButton.click();
        webClient.waitForBackgroundJavaScript(5000);
        return recentChangesPage;
    }
}
