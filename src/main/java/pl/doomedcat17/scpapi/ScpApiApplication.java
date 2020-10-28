package pl.doomedcat17.scpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ScpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScpApiApplication.class, args);
    }

}
