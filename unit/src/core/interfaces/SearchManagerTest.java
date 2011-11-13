package core.interfaces;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.Core;

public class SearchManagerTest {
	Core core;
	SearchManager searchManager;

	@Before
	public void setUp() throws Exception {
		//Set up the core first
		core = new Core();
		core.config().serverHostname("0.0.0.0");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		//Create a search manager
		searchManager=new SearchManager(core);
		//Set up search manager
		searchManager.filterComputes(true);
		searchManager.filterNetworks(true);
	}

	@After
	public void tearDown() throws Exception {
		core=null;
		searchManager=null;
	}


	@Test
	public final void testFilterComputes() {
		assertTrue("Compute filter broken ",searchManager.filterComputes());
	}

	@Test
	public final void testFilterNetworks() {
		assertTrue("Network filter broken ",searchManager.filterNetworks());
	}

	@Test
	public final void testCount() {
		String[] searchTags = {"hostname","network"};
		assertTrue("Failed to search",searchManager.search(searchTags));
		assertTrue("Got no results",searchManager.count()>0);
	}

	@Test
	public final void testItem() {
		String[] searchTags = {"hostname","network"};
		assertTrue("Failed to search",searchManager.search(searchTags));
		assertNotNull("Got null item",searchManager.item(0));
	}

	@Test
	public final void testSearch() {
		//Now test with nothing
		searchManager.filterComputes(false);
		searchManager.filterNetworks(false);
		String[] searchTags = {"nowaysuchitemisinserver"};
		assertTrue("Failed to search",searchManager.search(searchTags));
		assertTrue("Got no results",searchManager.count()==0);
	}

}
