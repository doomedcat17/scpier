package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import org.jsoup.nodes.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LastRevisionDateProvider {

    public static Date getLastRevision(Element contentBody) throws RevisionDateException {
        try {
            String fullTimeString = contentBody.selectFirst("#page-info >.odate").text();
            if (fullTimeString.contains("(")) {
                fullTimeString = fullTimeString.substring(0, fullTimeString.indexOf('(')).trim();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(fullTimeString);
        } catch (Exception e) {
            throw new RevisionDateException(e);
        }
    }
}
