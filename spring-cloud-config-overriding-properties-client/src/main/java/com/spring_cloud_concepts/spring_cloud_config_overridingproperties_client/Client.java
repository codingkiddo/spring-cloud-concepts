package com.spring_cloud_concepts.spring_cloud_config_overridingproperties_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Client {
	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}
}
