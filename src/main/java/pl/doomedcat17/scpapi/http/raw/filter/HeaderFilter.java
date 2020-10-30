package pl.doomedcat17.scpapi.http.raw.filter;

import java.util.regex.Matcher;

public class HeaderFilter extends Filter {

    public String[] getContent(String[] lines, int startIndex) {
        String line = lines[startIndex];
        Matcher strongStartMatcher = patternBox.getSTRONG_START_PATTERN().matcher(line);
        Matcher strongEndMatcher = patternBox.getSTRONG_END_PATTERN().matcher(line);
        Matcher paragraphEndMatcher = patternBox.getPARAGRAPH_END_PATTERN().matcher(line);
        if (strongStartMatcher.find() && strongEndMatcher.find() && paragraphEndMatcher.find()) {
            String[] content = new String[2];
            content[0] = changeHeaderTitle(line.substring(strongStartMatcher.end(), strongEndMatcher.start()));
            String headerContent = line.substring(strongEndMatcher.end(), paragraphEndMatcher.start());
            if (!headerContent.equals("")) {
                content[1] = clear(headerContent);
            } else content[1] = headerContent;
            lastIndex = startIndex;
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
}
