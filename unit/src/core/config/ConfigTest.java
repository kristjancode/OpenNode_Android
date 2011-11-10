package core.config;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {
	Config config;

	@Before
	public void setUp() throws Exception {
		config = new Config();
	}

	@After
	public void tearDown() throws Exception {
		config = null;
	}

	@Test
	public final void testConfig() {
		assertTrue("Failed to initialize config",config!=null);
	}

	@Test
	public final void testServerHostnameString() {
		String hostname = "localhost";
		config.serverHostname(hostname);
		assertTrue("Failed to retrieve set hostname", config.serverHostname().equals(hostname));
	}
	
	@Test
	public final void testServerHostnameNullString() {
		String hostname = null;
		config.serverHostname(hostname);
		assertTrue("Hostname is not String (nulls and others should not be allowed)",config.serverHostname() instanceof java.lang.String);
	}

	@Test
	public final void testServerPortInt() {
		int port = 9999;
		config.serverPort(port);
		assertTrue("Failed to retrieve set port", config.serverPort()==port);
	}

	@Test
	public final void testServerUsernameString() {
		String username = "username";
		config.serverUsername(username);
		assertTrue("Failed to retrieve set username", config.serverUsername().equals(username));
	}

	@Test
	public final void testServerUsernameNullString() {
		String username = null;
		config.serverUsername(username);
		assertTrue("Username is not String (nulls and others should not be allowed)",config.serverUsername() instanceof java.lang.String);
	}
	
	@Test
	public final void testServerPasswordString() {
		String password = "password";
		config.serverPassword(password);
		assertTrue("Failed to retrieve set password", config.serverPassword().equals(password));
	}

	@Test
	public final void testServerPasswordNullString() {
		String password = null;
		config.serverPassword(password);
		assertTrue("Password is not String (nulls and others should not be allowed)",config.serverPassword() instanceof java.lang.String);
	}

}
