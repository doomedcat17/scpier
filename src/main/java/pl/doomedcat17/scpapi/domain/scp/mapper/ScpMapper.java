package pl.doomedcat17.scpapi.domain.scp.mapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.HtmlMapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.HtmlMapperFactory;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.Patterns;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ScpMapper {


    public ScpObject mapToScp(Element content) {
        Map<String, String> mappedData = new HashMap<>();
        Elements data = content.children();
        String lastTitle = "";
        //TODO make appendix here
        for (Element element : data) {
            try {
                HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                Map<String, String> elementData = htmlMapper.mapElement(element);
                if (elementData.containsKey("title")) {
                    mappedData.put(
                            elementData.get("title"),
                            elementData.get("content")
                    );
                    lastTitle = elementData.get("title");
                } else {
                    mappedData.put(
                            lastTitle,
                            mappedData.get(lastTitle) + elementData.get("content")
                    );
                }
                return applyData(mappedData);
            } catch (MapperNotFoundException e) {
                log.info(e.getMessage());
            }
        }
        return null;
    }

    private ScpObject applyData(Map<String, String> mappedData) {
        ScpObject scpObject = new ScpObject();
        mappedData.keySet()
                .forEach(key -> setScpField(scpObject, key, mappedData));
        return null;
    }
    //TODO refactor
    private void setScpField(ScpObject scpObject, String key, Map<String, String> mappedData) {
      /*  String value = mappedData.get(key);
        switch (key) {
            case Patterns.SCP_NAME_PATTERN:
                scpObject.setObjectName(value);
                break;
            case Patterns.SCP_CLASS_PATTERN:
                scpObject.setObjectClass(value);
                break;
            case Patterns.SCP_PROCEDURES_PATTERN:
                scpObject.setContainmentProcedures(value);
                break;
            case Patterns.SCP_DESCRIPTION_PATTERN:
                scpObject.setDescription(value);
                break;
            default:
                if (scpObject.getAppendices() == null) {
                    scpObject.setAppendices(new ArrayList<>());
                }
                List<Appendix<?>> appendices = scpObject.getAppendices();
                Appendix<?> appendix = new Appendix<>();
                scpObject.getAppendices()
                        .add()
        }

       */
    }
}
