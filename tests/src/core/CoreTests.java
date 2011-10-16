package core;

import junit.framework.TestCase;

public class CoreTests extends TestCase {
	Core core;

	protected void setUp() throws Exception {
		core = new Core();
	}

	public void testServerConnector() {
		assertNotNull("Result", core.serverConnector());
	}

	public void testComputeManager() {
		assertNotNull("Result", core.computeManager());
	}

	public void testNetworkManager() {
		assertNotNull("Result", core.networkManager());
	}

	public void testStorageManager() {
		assertNotNull("Result", core.storageManager());
	}

	public void testTemplateManager() {
		assertNotNull("Result", core.templateManager());
	}

	public void testNewsManager() {
		//assertNotNull("Result", core.newsManager());
		assertNotNull("Result", core.templateManager());
	}

}
