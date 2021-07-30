package com.doomedcat17.scpier.scraper;

import com.doomedcat17.scpier.exception.scraper.ScraperNotDefinedException;
import com.doomedcat17.scpier.scraper.audio.AudioScraper;
import com.doomedcat17.scpier.scraper.blockquote.BlockquoteScraper;
import com.doomedcat17.scpier.scraper.div.DivScraper;
import com.doomedcat17.scpier.scraper.heading.HeadingScraper;
import com.doomedcat17.scpier.scraper.image.ImageScraper;
import com.doomedcat17.scpier.scraper.line.LineScraper;
import com.doomedcat17.scpier.scraper.list.ListScraper;
import com.doomedcat17.scpier.scraper.table.TableScraper;
import com.doomedcat17.scpier.scraper.video.VideoScraper;
import org.jsoup.nodes.Element;

public class ScraperFactory {

    public static ElementScraper getHtmlScrapper(Element element, String source) {
        switch (element.tagName()) {
            case "strong":
            case "em":
            case "p":
            case "a":
            case "b":
            case "li":
            case "small":
            case "span":
            case "sup":
            case "sub":
            case "tt":
            case "br":
            case "pre":
            case "code":
            case "script":
            case "dd":
            case "dt":
            case "i":
            case "u":
            case "font":
            case "del":
            case "strike":
            case "label":
                return new LineScraper(source);
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
            case "summary":
                return new HeadingScraper(source);
            case "audio":
            case "audio-player":
                return new AudioScraper(source);
            case "div":
            case "section":
            case "details":
            case "header":
            case "footer":
            case "article":
            case "form":
                //some divs has "blockquote class"
                if (element.hasClass("blockquote")) return new BlockquoteScraper(source);
                else return new DivScraper(source);
            case "img":
            case "picture":
                return new ImageScraper(source);
            case "table":
            case "tbody":
                return new TableScraper(source);
            case "ul":
            case "ol":
            case "dl":
                return new ListScraper(source);
            case "blockquote":
            case "center":
                return new BlockquoteScraper(source);
            case "video":
                return new VideoScraper(source);
            case "figure":
                if (element.selectFirst("img") != null) {
                    return new ImageScraper(source);
                } else return new VideoScraper(source);
            default:
                throw new ScraperNotDefinedException("Scrapper not defined for \"" + element.tagName() +"\" element");
        }
    }
}
