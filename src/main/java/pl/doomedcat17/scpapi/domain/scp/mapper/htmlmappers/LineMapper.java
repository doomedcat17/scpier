package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;

import java.util.List;

public class LineMapper implements HtmlMapper {
//TODO more tests needed
    @Override
    public void mapElement(Element element, List<Appendix> scpAppendices) {
        Appendix appendix = new Appendix();
        DeletedContentMarker.markDeletedContent(element);
        if (isNotSimpleParagraph(element)) {
            String[] extractedData = extractTitleAndText(element);
            element.select("span");
            appendix.setTitle(extractedData[0]);
            appendix.addContentBox(new ContentBox<>(ContentType.TEXT, extractedData[1]));
            scpAppendices.add(appendix);
        } else {
            ContentBox<String> contentBox = new ContentBox<>();
            contentBox.setContent(element.text().trim());
            scpAppendices.get(scpAppendices.size() - 1).addContentBox(contentBox);
        }
    }

    private boolean isNotSimpleParagraph(Element element) {
        Element strongElement = element.selectFirst("strong");
        //TODO common headings check (Addendum, Recovery Log, Document itp)
        if(strongElement != null) {
            if (ScpPattern.containsValue(strongElement.text(), "eng")) {
                return true;
            } else {
                return (strongElement.text().length() > 20);
            }
        } else return false;
    }


    private String[] extractTitleAndText(Element element) {
        String title = element
                .selectFirst("strong")
                .text().trim();
        String content = element
                .text()
                .substring(title.length())
                .trim();
        if (title.charAt(title.length() - 1) == ':')
            title = title.substring(0, title.length() - 1); //remove : from the end of title
        return new String[]{title, content};
    }
}
