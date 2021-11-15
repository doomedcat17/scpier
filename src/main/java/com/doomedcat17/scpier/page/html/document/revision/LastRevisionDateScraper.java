package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import org.jsoup.nodes.Element;
import java.util.Date;

public class LastRevisionDateScraper {

    public Date scrapDate(Element contentBody) throws RevisionDateException {
        try {
            Element timeElement = contentBody.selectFirst("#page-info > .odate");
            String className = timeElement.classNames()
                    .stream().filter(name -> name.startsWith("time_"))
                    .findFirst().orElseThrow(NullPointerException::new);
            String timestampStr = className.substring(className.indexOf("_")+1);
            return new Date(Integer.parseInt(timestampStr)* 1000L);
        } catch (NullPointerException e) {
            return new Date(0);
        } catch (Exception e) {
            throw new RevisionDateException(e);
        }
    }
}
