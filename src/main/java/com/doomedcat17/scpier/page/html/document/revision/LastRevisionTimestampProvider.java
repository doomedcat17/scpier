package com.doomedcat17.scpier.page.html.document.revision;

import com.doomedcat17.scpier.exception.page.html.document.revision.RevisionDateException;
import org.jsoup.nodes.Element;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class LastRevisionTimestampProvider {

    public static Timestamp getLastRevisionTimestamp(Element contentBody) throws RevisionDateException {
        try {
            String fullTimeString = contentBody.selectFirst("#page-info >.odate").text();
            if (fullTimeString.contains("(")) {
                fullTimeString = fullTimeString.substring(0, fullTimeString.indexOf('(')).trim();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return new Timestamp(dateFormat.parse(fullTimeString).getTime());
        } catch (NullPointerException e) {
            return new Timestamp(0);
        } catch (Exception e) {
            throw new RevisionDateException(e);
        }
    }
}
