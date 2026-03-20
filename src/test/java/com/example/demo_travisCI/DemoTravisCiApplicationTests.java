package com.example.demo_travisCI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoTravisCiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		DemoTravisCiApplication demo = new DemoTravisCiApplication();
		assertEquals(6, demo.sum(2, 3));
	}
}
