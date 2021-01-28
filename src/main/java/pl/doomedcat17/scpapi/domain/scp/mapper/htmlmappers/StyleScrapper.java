package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.StyleType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class StyleScrapper {
    public Map<String, String> scrapStyles(Element element) {
        Map<String, String> stylesMap = new HashMap<>();
        if (element.hasAttr("style")) {
            List<String> styles = Arrays.stream(element.attr("style")
                    .trim()
                    .split(";"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            styles.forEach(styleText -> mapStyle(styleText, stylesMap));
        }
        mapTag(element.tagName(), stylesMap);
        return stylesMap;

    }

    private void mapStyle(String style, Map<String, String> stylesMap) {
        String[] styleKeyAndValue = style.split(":");
        stylesMap.put(styleKeyAndValue[0].trim(), styleKeyAndValue[1].trim());
    }

    private void mapTag(String tagName, Map<String, String> stylesMap) {
        switch (tagName) {
            case "strong":
            case "b":
                stylesMap.put("font-weight", "bold");
                break;
            case "em":
            case "i":
                stylesMap.put("font-style", "italic");
                break;
            case "sup":
                stylesMap.put("vertical-align", "super");
                stylesMap.put("font-size", "smaller");
                break;
            case "sub":
                stylesMap.put("vertical-align", "sub");
                stylesMap.put("font-size", "smaller");
                break;
            case "p":
            case "span":
                break;
            default:
                log.error("StyleScrapper - style not found for: "+tagName);
        }
    }


}
