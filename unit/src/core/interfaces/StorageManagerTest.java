package core.interfaces;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Core;

public class StorageManagerTest {
	Core core;
	StorageManager storageManager;

	@Before
	public void setUp() throws Exception {
		core = new Core();
		core.config().serverHostname("localhost");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		storageManager = core.storageManager();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testItem() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testLoad() {
		fail("Not yet implemented"); // TODO
	}

}
