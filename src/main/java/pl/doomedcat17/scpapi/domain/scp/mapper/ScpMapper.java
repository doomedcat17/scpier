package pl.doomedcat17.scpapi.domain.scp.mapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.Appendix;
import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.ScpFieldsMapper;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.ScpPattern;
import pl.doomedcat17.scpapi.exceptions.ScpFieldNotFoundException;

import java.util.List;

@Slf4j
public class ScpMapper {

    public ScpObject mapToScp(PageContent pageContent) throws ScpFieldNotFoundException {
        Elements data = pageContent.getContent().children();
        ScpFieldsMapper scpFieldsMapper = new ScpFieldsMapper(data);
        List<Appendix<String>> scpFields = scpFieldsMapper.getScpNameAndClass();
        List<Appendix<?>> scpAppendices = scpFieldsMapper.getScpAppendices();
        return applyData(scpFields, scpAppendices, pageContent.getSourceUrl());
    }

    private ScpObject applyData(List<Appendix<String>> scpFields, List<Appendix<?>> scpAppendices, String source) throws ScpFieldNotFoundException {
        ScpObject scpObject = new ScpObject();
        applyScpFields(scpObject, scpFields);
        scpObject.setAppendices(scpAppendices);
        scpObject.setSource(source);
        return scpObject;
    }

    private void applyScpFields(ScpObject scpObject, List<Appendix<String>> scpFields) throws ScpFieldNotFoundException {
        for (Appendix<String> field: scpFields) {
            if (field.getTitle().equals(ScpPattern.OBJECT_NAME.engNormalized)) {
                scpObject.setObjectName(field.getContent());
            } else if (field.getTitle().equals(ScpPattern.OBJECT_CLASS.engNormalized)) {
                scpObject.setObjectClass(field.getContent());
            } else throw new ScpFieldNotFoundException("Scp field not found for '"+field.getTitle()+"'");
        }
    }

}
