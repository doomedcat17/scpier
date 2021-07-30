package com.doomedcat17.scpier.scraper.text;

import com.doomedcat17.scpier.exception.ElementScrapperException;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StyleScraper {

    /**
     * Returns a {@link Map} of local CSS style properties and values for given {@link Element}
     * @param element an {@link Element} 
     */
    public static Map<String, String> scrapStyles(Element element) {
        try {
            Map<String, String> stylesMap = new HashMap<>();
            mapTag(element.tagName(), stylesMap);
            if (element.hasAttr("style")) {
                List<String> styles = Arrays.stream(element.attr("style")
                                .trim()
                                .split(";"))
                        .map(String::trim)
                        .collect(Collectors.toList());
                styles.forEach(styleText -> getPropertyAndValue(styleText, stylesMap));
            }
            addClassStyles(element, stylesMap);
            return stylesMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ElementScrapperException(e.getMessage());
        }

    }

    /**
     * Adds property and value to given stylesMap
     * @param style a string of style property and value
     * @param stylesMap a {@link Map} of CSS style properties and values
     */
    private static void getPropertyAndValue(String style, Map<String, String> stylesMap) {
        //sometimes style is empty bruh
        if (!style.isBlank()) {
            try {
                String[] styleKeyAndValue = style.split(":");
                stylesMap.put(styleKeyAndValue[0].trim(), styleKeyAndValue[1].trim());
                //and sometimes style has no value
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    private static void addClassStyles(Element element, Map<String, String> stylesMap) {
        if (element.is(".center-header, .h6, .h5, .h4, .h3, .h2, .h1")) {
            stylesMap.put("font-weight", "bold");
        }
    }

    private static void mapTag(String tagName, Map<String, String> stylesMap) {
        switch (tagName) {
            case "strong":
            case "b":
            case "h4":
            case "dt":
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
            case "code":
            case "pre":
                stylesMap.put("font-family", "monospace");
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
            case "u":
                stylesMap.put("text-decoration", "underline");
                break;
            case "del":
            case "strike":
                stylesMap.put("text-decoration", "line-through");
                break;
            case "center":
                stylesMap.put("text-align", "center");
                break;
            case "p":
            case "span":
            default:
                break;
        }
    }


}
