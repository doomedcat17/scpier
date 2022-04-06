package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LastRevisionDateScraper {

    public LocalDateTime scrapDate(Element contentBody) throws RevisionDateException {
        try {
            Element timeElement = contentBody.selectFirst("#page-info > .odate");
            String className = timeElement.classNames()
                    .stream().filter(name -> name.startsWith("time_"))
                    .findFirst().orElseThrow(NullPointerException::new);
            String timestampStr = className.substring(className.indexOf("_")+1);
            return LocalDateTime.ofEpochSecond(Integer.parseInt(timestampStr), 0, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            return LocalDateTime.MIN;
        } catch (Exception e) {
            throw new RevisionDateException(e);
        }
    }
}
