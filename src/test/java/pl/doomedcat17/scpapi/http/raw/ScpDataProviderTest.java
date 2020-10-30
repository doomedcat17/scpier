package pl.doomedcat17.scpapi.http.raw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class ScpDataProviderTest {

    @Autowired
    private ScpDataProvider scpDataProvider;


    @Test
    void testaaa() {
        HashMap<String, String> content = scpDataProvider.getScpData("048");
        for (String key: content.keySet()) {
            System.out.println(key+": "+content.get(key));
        }
    }
}