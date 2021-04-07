package com.doomedcat17.scpier.scrapper.htmlscrappers;

import com.doomedcat17.scpier.scrapper.htmlscrappers.title.TitleResolver;
import com.doomedcat17.scpier.scrapper.htmlscrappers.audio.AudioScrapper;
import org.jsoup.nodes.Element;
import com.doomedcat17.scpier.scrapper.htmlscrappers.blockquote.BlockquoteScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.div.DivScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.heading.HeadingScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.image.ImageScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.line.LineScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.list.ListScrapper;
import com.doomedcat17.scpier.scrapper.htmlscrappers.table.TableScrapper;
import com.doomedcat17.scpier.exceptions.MapperNotFoundException;

public class HtmlScrapperFactory {

    public static ElementScrapper getHtmlScrapper(Element element, String source) throws MapperNotFoundException {
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
                throw new MapperNotFoundException("Could not find mapper for " + element.tagName());
        }
    }
}
