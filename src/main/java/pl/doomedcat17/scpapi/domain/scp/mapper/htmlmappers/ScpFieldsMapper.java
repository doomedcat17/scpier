package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.exceptions.MapperNotFoundException;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ScpFieldsMapper {

    private Elements elements;

    public List<Appendix> getScpNameAndClass() {
        return new ArrayList<>();
    }

    public List<Appendix> getScpAppendices() {
        List<Appendix> appendices = new ArrayList<>();
        for (Element element: elements) {
            try {
                HtmlMapper htmlMapper = HtmlMapperFactory.getHtmlMapper(element);
                htmlMapper.mapElement(element, appendices);
            } catch (MapperNotFoundException ignored) {
            }
        }
        return appendices;
    }
}
