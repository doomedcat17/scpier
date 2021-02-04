package domain.scp.mapper.htmlscrappers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import data.appendencies.Appendix;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrapperTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    protected Map<String, List<Appendix>> getExpectedAppendicesOutputs(String path) {
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        try {
            Map<String, List<Appendix>> outputs =
                    objectMapper.readValue(new File(path), new TypeReference<HashMap<String, List<Appendix>>>(){});
            return outputs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Element getSampleElements(String path) {
        return loadElementFormHTML(path)
                .getElementById("page-content");

    }

    private Element loadElementFormHTML(String path) {
        Element element = null;
        try {
            element = Jsoup.parse(
                    new File(path),
                    "UTF-8",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    protected void printObjectAsJson(Object objectToPrint) {
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectToPrint));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
