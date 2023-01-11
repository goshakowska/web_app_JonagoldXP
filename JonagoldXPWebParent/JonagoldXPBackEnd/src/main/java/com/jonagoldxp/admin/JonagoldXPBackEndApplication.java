package com.jonagoldxp.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EntityScan({"com.jonagoldxp.common.entity", "com.jonagoldxp.admin.user"}) // so the spring boot app will be able to locate the entity class
public class JonagoldXPBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(JonagoldXPBackEndApplication.class, args);
	}

}
