package pl.doomedcat17.scpapi.domain.scp.mapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;
import pl.doomedcat17.scpapi.domain.scp.mapper.htmlmappers.ScpFieldsMapper;

import java.util.List;

@Slf4j
public class ScpMapper {
//TODO code refactor
    public ScpObject mapToScp(PageContent pageContent) {
        List<Node> data = pageContent.getContent().childNodes();
        ScpFieldsMapper scpFieldsMapper = new ScpFieldsMapper(data);
        ScpObject scpObject = new ScpObject();
        scpFieldsMapper.mapScp(scpObject);
        return scpObject;
    }


}
