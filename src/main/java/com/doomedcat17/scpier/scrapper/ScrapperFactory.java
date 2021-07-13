package com.doomedcat17.scpier.scrapper;

import com.doomedcat17.scpier.exception.ScrapperNotDefinedException;
import com.doomedcat17.scpier.scrapper.audio.AudioScrapper;
import com.doomedcat17.scpier.scrapper.blockquote.BlockquoteScrapper;
import com.doomedcat17.scpier.scrapper.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.heading.HeadingScrapper;
import com.doomedcat17.scpier.scrapper.image.ImageScrapper;
import com.doomedcat17.scpier.scrapper.line.LineScrapper;
import com.doomedcat17.scpier.scrapper.list.ListScrapper;
import com.doomedcat17.scpier.scrapper.table.TableScrapper;
import org.jsoup.nodes.Element;

public class ScrapperFactory {

    public static ElementScrapper getHtmlScrapper(Element element, String source) {
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
            case "summary":
                return new LineScrapper(source);
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
                return new HeadingScrapper(source);
            case "audio":
            case "audio-player":
                return new AudioScrapper(source);
            case "div":
            case "section":
            case "details":
                if (element.hasClass("blockquote")) return new BlockquoteScrapper(source);
                else return new DivScrapper(source);
            case "img":
                return new ImageScrapper(source);
            case "table":
                return new TableScrapper(source);
            case "ul":
            case "ol":
                return new ListScrapper(source);
            case "blockquote":
                return new BlockquoteScrapper(source);
            default:
                throw new ScrapperNotDefinedException("Scrapper not defined for" + element.tagName() +" element");
        }
    }
}
