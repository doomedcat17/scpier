package pl.doomedcat17.scpapi;

import pl.doomedcat17.scpapi.data.appendencies.Appendix;
import java.util.List;
import java.util.Map;

public class TagFinder {

    public static void main(String[] args) {
        Map<String, List<Appendix>> expectedOutputs = TestDataProvider
                .getExpectedAppendicesOutputs("src/test/resources/html/test_data/lists/expected_outputs.json");
        List<Appendix> list = expectedOutputs.get("sampleUnorderedList");
        System.out.println(list);
    }
}
