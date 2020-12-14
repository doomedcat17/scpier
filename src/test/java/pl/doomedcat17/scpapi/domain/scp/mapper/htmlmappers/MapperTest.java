package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.junit.jupiter.api.BeforeEach;
import pl.doomedcat17.scpapi.TestDataProvider;
import pl.doomedcat17.scpapi.data.ScpObject;

public class MapperTest {

    protected ScpObject scpObject;

    @BeforeEach
    void init() {
        scpObject = TestDataProvider.getSampleScpObject();
    }

    protected void addSampleAppendixToScpObject() {
        scpObject.addAppendix(TestDataProvider.getSampleAppendix());
    }
}
