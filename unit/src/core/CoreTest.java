package core;

import static org.junit.Assert.*;
import org.junit.*;

public class CoreTest 
{	
	Core core;
	
	@Before
	public void setUp() throws Exception {
		//Needs to run in emulator, thus 10.0.2.2
		core = new Core();
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