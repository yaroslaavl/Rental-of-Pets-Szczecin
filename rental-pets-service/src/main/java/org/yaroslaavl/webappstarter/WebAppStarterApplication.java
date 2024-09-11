package org.yaroslaavl.webappstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebAppStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAppStarterApplication.class, args);

    }
}
