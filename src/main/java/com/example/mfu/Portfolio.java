package com.example.mfu;

import com.example.mfu.config.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class Portfolio extends SpringBootServletInitializer {

	@Autowired
	private EmailSenderService service;

	public static void main(String[] args) {
		SpringApplication.run(Portfolio.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail() {
//		service.sendEmail("wuxd1302@gmail.com",
//				"sub", "body");
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(Portfolio.class);
	}
}
