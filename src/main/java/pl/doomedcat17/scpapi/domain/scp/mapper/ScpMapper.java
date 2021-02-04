package pl.doomedcat17.scpapi.domain.scp.mapper;

import org.jsoup.nodes.Node;
import pl.doomedcat17.scpapi.data.scp.ScpObject;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;

import java.util.List;

public class ScpMapper {
//TODO code refactor
    public ScpObject mapToScp(PageContent pageContent) {
        List<Node> data = pageContent.getContent().childNodes();
        ScpFieldsMapper scpFieldsMapper = new ScpFieldsMapper(data);
        ScpObject scpObject = new ScpObject();
        scpObject.setObjectName(pageContent.getScpName());
        scpFieldsMapper.mapScp(scpObject);
        return scpObject;
    }


}
