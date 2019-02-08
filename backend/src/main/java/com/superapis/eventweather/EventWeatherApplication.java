package com.superapis.eventweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource("classpath:keys.properties")
public class EventWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventWeatherApplication.class, args);
	}

}
