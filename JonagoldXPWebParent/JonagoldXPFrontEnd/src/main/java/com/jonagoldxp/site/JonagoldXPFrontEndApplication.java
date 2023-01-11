package com.jonagoldxp.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan({"com.jonagoldxp.customer", "com.jonagoldxp.common.entity"})
public class JonagoldXPFrontEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(JonagoldXPFrontEndApplication.class, args);
    }

}
