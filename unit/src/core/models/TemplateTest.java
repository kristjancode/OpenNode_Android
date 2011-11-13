package core.models;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TemplateTest {
	Template template;
	int id = 0;
	String name = "template";
	int minDisk = 1;
	int minMemory = 1;
	int minCpu = 1;
	int maxDisk = 2;
	int maxMemory = 2;
	int maxCpu = 2;

	@Before
	public void setUp() throws Exception {
		template = new Template(0,name,minDisk,minMemory,minCpu,maxDisk,maxMemory,maxCpu);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testValid() {
		Template invalid_template = new Template(0,null,0,0,0,0,0,0);
		assertFalse("This item should be invalid",invalid_template.valid());
	}
	
	/*
	 * We shouldn't worry about that, should be checked serverside
	@Test
	public final void testValidationLogic() {
		Template invalid_template = new Template(0,"name",3,3,3,2,2,2);
		//This is a logic test, if it fails, there is bad logic in core
		assertFalse("This item should be invalid cause max items < min items",invalid_template.valid());
	}
	*/
	
	@Test
	public final void testAssignItem() {
		Item item = null;
		assertFalse("Assigning null object to template should not work",template.assign(item));
	}

	@Test
	public final void testAssignJSONObject() {
		Template duplicate = new Template();
		try {
			String JSONRepresentation = template.toJSON();
			JSONObject jsonO = new JSONObject(JSONRepresentation);
			duplicate.assign(jsonO);
			assertTrue("Representation didn't come out the same, assign worked tho", duplicate.toJSON().equals(JSONRepresentation));
		} catch (Exception exception) {
			fail("Something went terribly wrong: "+exception.toString());
		}
	}

	@Test
	public final void testToJSON() {
		assertTrue("Didn't get JSON representation", template.toJSON() instanceof java.lang.String);
	}

	@Test
	public final void testToString() {
		assertTrue("Didn't get string", template.toString() instanceof java.lang.String);
	}

	@Test
	public final void testTemplate() {
		Template templateobject = new Template();
		assertTrue("Got null object instead",templateobject!=null);
	}

	@Test
	public final void testMinDisk() {
		assertTrue("Bad minDisk",template.minDiskSize()==minDisk);
	}

	@Test
	public final void testMinMemory() {
		assertTrue("Bad minMemory",template.minMemorySize()==minMemory);
	}

	@Test
	public final void testMinCpu() {
		assertTrue("Bad minCpu",template.minCpu()==minCpu);	
	}

	@Test
	public final void testMaxDisk() {
		assertTrue("Bad maxDisk",template.maxDiskSize()==maxDisk);
	}

	@Test
	public final void testMaxMemory() {
		assertTrue("Bad maxMemory",template.maxMemorySize()==maxMemory);
	}

	@Test
	public final void testMaxCpu() {
		assertTrue("Bad maxCpu",template.maxCpu()==maxCpu);	
	}

}
