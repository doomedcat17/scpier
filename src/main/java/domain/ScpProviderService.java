package domain;

import data.scp.ScpObject;

public interface ScpProviderService {

    ScpObject getScpObject(String objectId);
}
