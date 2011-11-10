package core.interfaces;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Core;
import core.models.Network;

public class NetworkManagerTest {
	Core core;
	NetworkManager networkManager;

	@Before
	public void setUp() throws Exception {
		core = new Core();
		core.config().serverHostname("localhost");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		networkManager = core.networkManager();
	}

//	@After
	public void tearDown() throws Exception {
		core = null;
	}

	@Test
	public final void testLoad(){
		assertTrue("Loading fail",networkManager.load());
	}

	@Test
	public final void testCountItems(){
		assertTrue("Failed to load",networkManager.load());
		assertTrue("Should be able to count at least some items",networkManager.count()>0);
	}

	@Test
	public final void testCreateWithNullMask() {
		Network network = new Network(0,"name",null,null,null,null);
		assertFalse("Should not be able to make network with nulls",networkManager.create(network));
	}

	@Test
	public final void testCreateDuplicateId() {
		assertTrue("Failed to load",networkManager.load());
		assertTrue("Fail; didn't get item",networkManager.item(1) instanceof Network);
		String name=networkManager.item(1).name();
		Network duplicateNetwork = new Network(22,"duplicate","asd","asd","asd","asd");
		assertTrue("Didn't manage to create such item",networkManager.create(duplicateNetwork));
		assertTrue("Failed to load",networkManager.load());
		assertTrue("Name should remain the same at that id",name.equals(networkManager.item(1).name()));
	}

}
