package io.pivotal.microservices.services.accounts;

import io.pivotal.microservices.accounts.AccountRepository;
import io.pivotal.microservices.accounts.AccountsController;
import io.pivotal.microservices.accounts.AccountsInfrastructureConfig;
import io.pivotal.microservices.accounts.AccountsWebApplication;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * 
 * @author Paul Chapman
 */
@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(AccountsWebApplication.class)
public class AccountsServer {

	@Autowired
	protected AccountRepository accountRepository;

	protected Logger logger = Logger
			.getLogger(AccountsInfrastructureConfig.class.getName());

	public static void main(String[] args) {
		// Tell server to look for accounts-server.properties or
		// accounts-server.yml
		System.setProperty("spring.config.name", "accounts-server");

		SpringApplication.run(AccountsServer.class, args);
	}

	@Bean
	public AccountsController accountsController() {
		return new AccountsController(accountRepository);
	}
}
