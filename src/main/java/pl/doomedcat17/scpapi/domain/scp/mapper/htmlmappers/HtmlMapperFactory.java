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
                return new LineMapper();
           /* case "div":
                return new DivMapper(); */
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
