package pl.doomedcat17.scpapi.domain;

import pl.doomedcat17.scpapi.data.ScpObject;

public interface ScpProviderService {

    ScpObject getScpObject(String objectId);
}
