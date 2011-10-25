package core;

import core.config.Config;
import junit.framework.TestCase;

public class CoreTests extends TestCase {
	Core core;

	protected void setUp() throws Exception {
		//Needs to run in emulator, thus 10.0.2.2
		Config.SERVER_HOSTNAME = "10.0.2.2";
		core = new Core();
	}

	public void testCore() {
		assertNotNull(core);
	}

	public void testServerConnector() {
		assertNotNull(core.serverConnector());
	}

	public void testComputeManager() {
		assertNotNull(core.computeManager());
	}

	public void testNetworkManager() {
		assertNotNull(core.networkManager());
	}

	public void testStorageManager() {
		assertNotNull(core.storageManager());
	}

	public void testTemplateManager() {
		assertNotNull(core.templateManager());
	}

	public void testNewsManager() {
		assertNotNull(core.newsManager());
	}

}
