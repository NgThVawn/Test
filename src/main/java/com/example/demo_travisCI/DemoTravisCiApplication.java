package com.example.demo_travisCI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoTravisCiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTravisCiApplication.class, args);
	}
	public int sum(int a, int b) {
		return a + b;
	}
}
