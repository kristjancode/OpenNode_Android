package core.interfaces;

import static org.junit.Assert.*;
import org.junit.*;

import core.Core;
import core.models.Compute;

public class ComputeManagerTest {

	Core core;
	ComputeManager computeManager;
	
	@Before
	public void setUp() throws Exception {
		core = new Core();
		core.config().serverHostname("localhost");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		computeManager = core.computeManager();
	}
	
	@Test
	public void testComputeManager() {
		assertNotNull(computeManager);
	}
	
	@Test
	public void testNegativeIndexItem() {
		assertTrue("Failed to load",computeManager.load());
		assertNull("There shouldn't be anything on negative index",computeManager.item(-1));
	}
	
	@Test
	public void testCount(){
		assertTrue("Failed to load",computeManager.load());
		assertTrue("Should count some items",computeManager.count()>0);
	}
	
	@Test
	public void testDetails() {
		assertTrue(computeManager.load());
	}
	
	//@Test
	//If this fails, it's probably server side
	public void testCreateWithSpecial() {
		//Create new compute with special characters
		Compute compute = new Compute(Integer.MAX_VALUE, "Nårska täcken", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE, "state", "template");
		//Try to add it
		assertTrue("Failed to add Compute",computeManager.create(compute));
		//Try to get it back (assume it is last element)
		assertEquals("Either compute wasn't last item or something is broken",compute.name(),computeManager.item(computeManager.count()-1).name());
	}
	
	@Test
	public void testWithNullString(){
		Compute compute = new Compute(Integer.MAX_VALUE, null, "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE, "state", "template");
		assertFalse("Should not be able to make item with nullstring.",computeManager.create(compute));
		//assertEquals("Either compute wasn't last item or something is broken",compute.name(),computeManager.item(computeManager.count()-1).name());
	}

	@Test
	public void testCreateEmptyItem(){
		Compute compute = new Compute();
		//Should not be able do add empty item
		assertFalse(computeManager.create(compute));
	}
	
	@Test
	public void testCreateItem(){
		assertTrue("Failed to load",computeManager.load());
		Compute compute = new Compute(20,"computename","arch", 77, (float) 1.5, 88, "state", "template");
		assertTrue("Invalid compute",compute.valid());
		assertTrue("Should be able to create damn compute",computeManager.create(compute));
	}
	
	@Test
	public void testDelete() {
		Compute compute = new Compute();
		assertFalse(computeManager.delete(compute));
	}

}
