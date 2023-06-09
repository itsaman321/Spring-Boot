package com.cognizant.menuservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MenuServiceApplication {

	@Bean
	@LoadBalanced
	public WebClient webClient(){
		return WebClient.builder().build() ;
	}

	public static void main(String[] args) {
		SpringApplication.run(MenuServiceApplication.class, args);
	}

}
