package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeaderFilter { //TODO change classname

    private Pattern firstPattern = Pattern.compile("(<strong>)");
    private Pattern secondPattern = Pattern.compile("(<\\/strong>)");
    private Pattern thirdPattern = Pattern.compile("(<\\/p>)");

    public String[] getHeaderFromLine(String line) {
        Matcher firstMatcher = firstPattern.matcher(line); //TODO code refactor
        Matcher secondMatcher = secondPattern.matcher(line);
        Matcher thirdMatcher = thirdPattern.matcher(line);
        if (firstMatcher.find() && secondMatcher.find() && thirdMatcher.find()) {
            String[] content = new String[2];
            content[0] = changeHeaderTitle(line.substring(firstMatcher.end(), secondMatcher.start()));
            String headerContent = line.substring(secondMatcher.end(), thirdMatcher.start());
            if (!headerContent.equals("")) {
                content[1] = clearText(headerContent);
            } else content[1] = headerContent;
            return content;
        } else throw new RuntimeException();

    }

    private String changeHeaderTitle(String originalTitle) {
        String title = originalTitle;
        switch (originalTitle) {
            case "Item #:":
                title = "objectName";
                break;
            case "Object Class:":
                title = "objectClass";
                break;
            case "Description:":
                title = "objectDescription";
                break;
            case "Special Containment Procedures:":
                title = "objectSpecialContainmentProcedures";
                break;
            default:
                if (originalTitle.charAt(originalTitle.length()-1) == ':') {
                    title = originalTitle.substring(0, originalTitle.length() - 1);
                }
        }
        return title;
    }

    private String clearText(String text) {
        String outputText = text;
        if (text.charAt(0) == ' ') outputText = text.substring(1);
        if (text.charAt(text.length()-1) == ' ') outputText = text.substring(0, text.length()-1);
        return outputText;
    }
}
