package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloggingAppApplication{
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		System.out.println(this.passwordEncoder.encode("xyz"));
//	}
}
