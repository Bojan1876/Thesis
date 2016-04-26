package dmi.uns.ac.rs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import rs.ac.uns.dmi.VmServiceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VmServiceApplication.class)
@WebAppConfiguration
public class VmServiceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
