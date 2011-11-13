package core.models;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputeTest {
	Compute compute;

	@Before
	public void setUp() throws Exception {
		compute = new Compute(Integer.MAX_VALUE, "name", "arch", Integer.MAX_VALUE, (float) 0.0, Integer.MAX_VALUE, "state", "template");
	}

	@After
	public void tearDown() throws Exception {
		compute=null;
	}
	/*
	@Test
	public void testAssignItem() {
		//Fails and that's good
		assertFalse(compute.assign(null));
		Compute compute2 = new Compute();
		assertTrue(compute.assign(compute2));
	}
	*/
	@Test
	public void testToJSON() {
		JSONObject jsonObject = new JSONObject();
		assertFalse(jsonObject.toString().equals(compute.toJSON()));
	}

	@Test
	public void testCompute() {
		assertNotNull(compute);
	}

	@Test
	public void testArch() {
		assertEquals("arch",compute.arch());
	}

	@Test
	public void testCores() {
		assertEquals(Integer.MAX_VALUE,compute.cores());
	}

	@Test
	public void testCpu() {
		//Delta=1?
		assertEquals((float) 0.0,compute.cpu(),1);
	}
	
	@Test
	public void testMemory() {
		assertEquals(Integer.MAX_VALUE,compute.memory());
	}
	
	@Test
	public void testState() {
		assertEquals("state",compute.state());
	}

	@Test
	public void testTemplate() {
		assertEquals("template",compute.template());
	}

}
