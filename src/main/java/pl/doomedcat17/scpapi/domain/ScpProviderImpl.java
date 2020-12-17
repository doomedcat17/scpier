package pl.doomedcat17.scpapi.domain;

import pl.doomedcat17.scpapi.data.ScpObject;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContent;
import pl.doomedcat17.scpapi.domain.scp.http.page_content.PageContentProvider;
import pl.doomedcat17.scpapi.domain.scp.mapper.ScpMapper;

import java.io.IOException;

public class ScpProviderImpl implements ScpProviderService{

    private final String SCP_URL = "http://www.scpwiki.com/";

    private PageContentProvider pageContentProvider = new PageContentProvider();

    private ScpMapper scpMapper = new ScpMapper();


    @Override
    public ScpObject getScpObject(int objectId) {
        String source = SCP_URL+"scp-"+objectId;
        ScpObject scpObject = null;
        try {
            PageContent pageContent = pageContentProvider.getPageContent(source);
            scpObject = scpMapper.mapToScp(pageContent);
            scpObject.setSource(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scpObject;
    }
}
