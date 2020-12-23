package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.Appendix;

import static org.junit.jupiter.api.Assertions.*;

class DivMapperTest {



    private DivMapper divMapper = new DivMapper();

    private final Element sampleDivs = TestDataProvider
            .getSampleElements("src/test/resources/html/test_data/divs/SampleDivsElements.html");

    @Test
    void shouldMapDiv1() {
        //given
        Element simpleDiv = sampleDivs.getElementById("shouldMapDiv1");
        //when
        Appendix appendix = divMapper.mapElement(simpleDiv);
        //then
        System.out.println("Papiesz");

    }

}