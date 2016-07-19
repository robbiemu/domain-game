package xyz.selfenrichment.spring_data_rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import xyz.personalenrichment.domain.SpringDataRestApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestApplication.class)
@WebAppConfiguration
public class SpringDataRestApplicationTests {

	@Test
	public void contextLoads() {
	}

}
