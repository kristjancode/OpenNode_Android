package core;

import static org.junit.Assert.*;
import org.junit.*;

public class CoreTest 
{	
	Core core;
	
	@Before
	public void setUp() throws Exception {
		//No longer needs to run in emulator, thus 0.0.0.0
		core = new Core();
		//OOH new feature, we need to actually authenticate
		core.config().serverHostname("0.0.0.0");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
	}

	@Test
	public void testCore() {
		assertNotNull(core);
	}
	
	@Test
	public void testServerConnector() {
		assertNotNull(core.serverConnector());
	}

	@Test
	public void testComputeManager() {
		assertNotNull(core.computeManager());
	}
	
	@Test
	public void testNetworkManager() {
		assertNotNull(core.networkManager());
	}

	@Test
	public void testStorageManager() {
		assertNotNull(core.storageManager());
	}

	@Test
	public void testTemplateManager() {
		assertNotNull(core.templateManager());
	}

	@Test
	public void testNewsManager() {
		assertNotNull(core.newsManager());
	}
	
	@After 
	public void tearDown() {
		core = null;
	}
}