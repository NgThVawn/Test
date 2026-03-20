package laptrinhJ2EE.nguyenthanhvan;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class NguyenthanhvanApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void ciIntentionalFailDemo() {
		String failSwitch = System.getenv().getOrDefault("TRAVIS_DEMO_FORCE_FAIL", "false");
		assertNotEquals("true", failSwitch,
				"Intentional CI fail demo: set TRAVIS_DEMO_FORCE_FAIL=false in .travis.yml then re-run.");
	}

}
