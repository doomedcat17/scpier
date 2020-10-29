package pl.doomedcat17.scpapi.http.raw.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UlListFilterTest {

    private UlListFilter ulListFilter = new UlListFilter();

    String text = "<ul>\n" +
            "<li>A selection of fiction books, preferably of the romance genre - Approved</li>\n" +
            "<li>Portable music player - Denied</li>\n" +
            "<li>A phone call - Denied</li>\n" +
            "<li>Cat food - Approval pending</li>\n" +
            "</ul>";

    String[] lines = text.split("\n");

    @Test
    void shouldGetUlContent() {
        String content = ulListFilter.getListContent(lines, 0);
        System.out.println(content);
    }

}