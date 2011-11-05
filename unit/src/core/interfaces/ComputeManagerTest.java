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
		computeManager = core.computeManager();
	}
	
	@Test
	public void testComputeManager() {
		assertNotNull(computeManager);
	}
	
	@Test
	public void testItem() {
		assertNull("There shouldn't be anything on negative index",computeManager.item(-1));
	}

	@Test
	public void testDetails() {
		assertTrue(computeManager.load());
	}
	
	@Test
	public void testCreateWithSpecial() {
		//Create new compute with special characters
		Compute compute = new Compute(Integer.MAX_VALUE, "Nårska täcken", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE+1, "state", "template");
		//Try to add it
		assertTrue(computeManager.create(compute));
		//Try to get it back (assume it is last element)
		assertEquals("Either compute wasn't last item or something is broken",compute.name(),computeManager.item(computeManager.count()-1).name());
	}
	
	//@Test
	//Skip this test as it will kill demoserver
	public void testWithNullString(){
		Compute compute = new Compute(Integer.MAX_VALUE, null, "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE+1, "state", "template");
		assertTrue("Failed to add Compute",computeManager.create(compute));
		assertEquals("Either compute wasn't last item or something is broken",compute.name(),computeManager.item(computeManager.count()-1).name());
	}

	//@Test
	//Skip this test as empty compute will kill demoserver
	public void testCreateEmptyItem(){
		Compute compute = new Compute();
		assertTrue(computeManager.create(compute));
	}
	
	@Test
	public void testDelete() {
		Compute compute = new Compute();
		assertFalse(computeManager.delete(compute));
	}

}
