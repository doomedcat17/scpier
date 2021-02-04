package domain.scp.mapper.htmlscrappers.text;

import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StyleScrapper {
    public Map<String, String> scrapStyles(Element element) {
        Map<String, String> stylesMap = new HashMap<>();
        mapTag(element.tagName(), stylesMap);
        if (element.hasAttr("style")) {
            List<String> styles = Arrays.stream(element.attr("style")
                    .trim()
                    .split(";"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            styles.forEach(styleText -> mapStyle(styleText, stylesMap));
        }
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
            case "h4":
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
            case "h1":
                stylesMap.put("font-size", "2em");
                stylesMap.put("font-weight", "bold");
                break;
            case "h2":
                stylesMap.put("font-size", "1.5em");
                stylesMap.put("font-weight", "bold");
                break;
            case "h3":
                stylesMap.put("font-size", "1.17em");
                stylesMap.put("font-weight", "bold");
                break;
            case "h5":
                stylesMap.put("font-size", ".83em");
                stylesMap.put("font-weight", "bold");
                break;
            case "h6":
                stylesMap.put("font-size", ".67em");
                stylesMap.put("font-weight", "bold");
                break;
            case "a":
                stylesMap.put("color", "#901");
                break;
            case "p":
            case "span":
                break;
            default:
        }
    }


}
