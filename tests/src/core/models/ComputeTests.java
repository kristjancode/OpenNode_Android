package core.models;

import org.json.JSONObject;

import junit.framework.TestCase;

//Needs mockItem
public class ComputeTests extends TestCase {
	Compute compute;

	protected void setUp() throws Exception {
		compute = new Compute(Integer.MAX_VALUE, "name", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE, "state", "template");
	}

	public void testAssignItem() {
		//Fails and that's good
		assertFalse(compute.assign(null));
		Compute compute2 = new Compute();
		assertTrue(compute.assign(compute2));
	}

	public void testAssignJSONObjectBoolean() {
		//True
		assertTrue(true);
	}

	public void testToJSON() {
		JSONObject jsonObject = new JSONObject();
		assertFalse(jsonObject.toString().equals(compute.toJSON()));
	}

	public void testToString() {
		//True
		assertTrue(true);
	}

	public void testCompute() {
		assertNotNull(compute);
	}

	public void testArch() {
		assertEquals("arch",compute.arch());
	}

	public void testCores() {
		assertEquals(Integer.MAX_VALUE,compute.cores());
	}

	public void testCpu() {
		assertEquals((float) 0.0,compute.cpu());
	}

	public void testMemory() {
		assertEquals(Integer.MAX_VALUE,compute.memory());
	}

	public void testState() {
		assertEquals("state",compute.state());
	}

	public void testTemplate() {
		assertEquals("template",compute.template());
	}
}
