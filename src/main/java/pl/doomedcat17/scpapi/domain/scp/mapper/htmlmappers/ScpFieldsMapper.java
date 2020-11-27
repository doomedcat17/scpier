package pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers;

import lombok.AllArgsConstructor;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ScpFieldsMapper {

    private Elements elements;

    public List<Appendix<String>> getScpNameAndClass() {
        return new ArrayList<>();
    }

    public List<Appendix<?>> getScpAppendices() {
        return new ArrayList<>();
    }
}
