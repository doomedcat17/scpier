package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import org.junit.jupiter.api.BeforeEach;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ContentBox;
import pl.doomedcat17.scpapi.data.ContentType;
import pl.doomedcat17.scpapi.data.ScpObject;

public class MapperTest {

    protected ScpObject scpObject;

    protected final String testTitle = "Test title";

    protected final ContentBox<String> testContentBox = new ContentBox<>(ContentType.TEXT, "Test text inside box");

    @BeforeEach
    void init() {
        scpObject = new ScpObject();
        Appendix testAppendix = new Appendix();
        testAppendix.setTitle(testTitle);
        testAppendix.addContentBox(testContentBox);
        scpObject.addAppendix(testAppendix);
    }
}
