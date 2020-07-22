package com.tebon.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AppConfig {

	@Bean
	public TestDao testDao(){
		return new TestDao();
	}
}
