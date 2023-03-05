package senberg.zavertest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
@SpringBootTest
public
class TestApplication {

	@Test
	void contextLoads() {
	}

	@MockBean
	private Application mockedApplication;
}
