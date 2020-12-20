package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

public class HtmlMapperFactory {

    public static HtmlMapper getHtmlMapper(Element element) throws MapperNotFoundException {
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
                return new LineMapper();
            case "h1":
            case "h2":
            case "h3":
            case "h4":
            case "h5":
            case "h6":
                return new HeadingMapper();
            case "div":
                return new DivMapper();
            case "img":
                return new ImageMapper();
            case "table":
                return new TableMapper();
            case "ul":
            case "ol":
                return new ListMapper();
            case "blockquote":
                return new BlockquoteMapper();
            default:
                throw new MapperNotFoundException("Could not find mapper for " + element.tagName());
        }
    }
}
