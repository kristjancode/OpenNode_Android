package core.interfaces;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Core;
import core.models.Storage;

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
	public void testStorageManager() {
		assertNotNull(storageManager);
	}

	@Test
	public void testCreateItem(){
		assertTrue("Failed to load",storageManager.load());
		int count = storageManager.count();
		Storage storage = new Storage(0,"name","type",9,1);
		assertTrue("Invalid storage",storage.valid());
		assertTrue("Should be able to create damn storage",storageManager.create(storage));
		assertTrue("Failed to load second time",storageManager.load());
		assertEquals("New item not counted",storageManager.count()+1==count);
	}

	@Test
	public final void testLoad() {
		assertTrue("Failed to load",storageManager.load());
	}

}
