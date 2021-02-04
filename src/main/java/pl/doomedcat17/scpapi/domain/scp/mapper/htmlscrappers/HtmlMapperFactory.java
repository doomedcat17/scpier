package pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.blockquote.BlockquoteScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.div.DivScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.heading.HeadingScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.image.ImageScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.line.LineScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.list.ListScrapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlscrappers.table.TableScrapper;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

public class HtmlMapperFactory {

    public static HtmlScrapper getHtmlMapper(Element element) throws MapperNotFoundException {
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
                return new LineScrapper();
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
                return new HeadingScrapper();
            case "div":
                return new DivScrapper();
            case "img":
                return new ImageScrapper();
            case "table":
                return new TableScrapper();
            case "ul":
            case "ol":
                return new ListScrapper();
            case "blockquote":
                return new BlockquoteScrapper();
            default:
                throw new MapperNotFoundException("Could not find mapper for " + element.tagName());
        }
    }
}
