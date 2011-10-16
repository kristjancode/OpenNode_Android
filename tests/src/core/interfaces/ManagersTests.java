package core.interfaces;

import core.Core;
import core.config.Config;
import core.models.Compute;
import junit.framework.TestCase;

public class ManagersTests extends TestCase {
	Core core;
	ComputeManager computeManager;
	NetworkManager networkManager;
	NewsManager newsManager;
	StorageManager storageManager;
	TemplateManager templateManager;

	protected void setUp() throws Exception {
		Config.SERVER_HOSTNAME = "10.0.2.2";
		core = new Core();
		computeManager = core.computeManager();
		networkManager = core.networkManager();
		newsManager = core.newsManager();
		storageManager = core.storageManager();
		templateManager = core.templateManager();
	}
	
	public void testComputeManager() {
		assertTrue(computeManager.load());
		Compute compute = new Compute(Integer.MAX_VALUE, "name", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE, "state", "template");
		int count = computeManager.count();
		assertTrue(computeManager.create(compute));
		assertEquals(count+1,computeManager.count());
	}

}
