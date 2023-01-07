package com.jonagoldxp.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.jonagoldxp.customer", "com.jonagoldxp.common.entity"})
public class JonagoldXPFrontEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(JonagoldXPFrontEndApplication.class, args);
    }

}
