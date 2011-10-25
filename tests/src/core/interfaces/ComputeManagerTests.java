package core.interfaces;

import core.Core;
import core.config.Config;
import core.models.Compute;
import junit.framework.TestCase;

public class ComputeManagerTests extends TestCase {

	Core core;
	ComputeManager computeManager;

	protected void setUp() throws Exception {
		//Needs to run in emulator, thus 10.0.2.2
		Config.SERVER_HOSTNAME = "10.0.2.2";

		core = new Core();
		computeManager = core.computeManager();

	}

	public void testComputeManager() {
		assertNotNull(computeManager);
	}

	public void testItemInstance() {
		fail("Not yet implemented");
	}

	public void testCount() {
		fail("Not yet implemented");
	}

	public void testItem() {
		assertNull("There shouldn't be anything on negative index",computeManager.item(-1));
	}

	public void testLoad() {
		fail("Not yet implemented");
	}

	public void testDetails() {
		assertTrue(computeManager.load());
	}

	public void testCreate() {
		//Create new compute with special characters
		Compute compute = new Compute(Integer.MAX_VALUE, "Nårska täcken", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE+1, "state", "template");

		//Create with null string
		//Compute compute = new Compute(Integer.MAX_VALUE, null, "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE+1, "state", "template");
		//Try to add it
		assertTrue(computeManager.create(compute));
		//Try to get it back (assume it is last element)
		assertEquals(compute.name(),computeManager.item(computeManager.count()-1).name());
	}
/*
	public void testCreateEmptyItem(){
		Compute compute = new Compute();
		assertTrue(computeManager.create(compute));
	}
	*/

	public void testUpdate() {
		fail("Not yet implemented");
	}

	public void testDelete() {
		Compute compute = new Compute();
		assertFalse(computeManager.delete(compute));
	}

	public void testItemInstance1() {
		fail("Not yet implemented");
	}

}
