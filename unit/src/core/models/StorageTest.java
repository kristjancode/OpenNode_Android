package core.models;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
	Storage storage;
	int id = 0;
	String name = "name";
	String type = "type";
	int capacity = 9;
	int available = 1;

	@Before
	public void setUp() throws Exception {
		storage = new Storage(id,name,type,capacity,available);
	}

	@After
	public void tearDown() throws Exception {
		storage=null;
	}

	@Test
	public final void testValid() {
		assertTrue("This storage item should be valid",storage.valid());
		//also make one that is invalid
		Storage invalid_storage = new Storage(0,null,null,0,0);
		assertFalse("This storage item should be invalid",invalid_storage.valid());
	}

	@Test
	public final void testAssignItem() {
		Item item = null;
		assertFalse("Assigning null object to network should not work",storage.assign(item));
	}

	@Test
	public final void testAssignJSONObject() {
		Storage duplicate = new Storage();
		try {
			String JSONRepresentation = storage.toJSON();
			JSONObject jsonO = new JSONObject(JSONRepresentation);
			duplicate.assign(jsonO);
			assertTrue("Representation didn't come out the same, assign worked tho", duplicate.toJSON().equals(JSONRepresentation));
		} catch (Exception exception) {
			fail("Something went terribly wrong: "+exception.toString());
		}
	}

	@Test
	public final void testToJSON() {
		assertTrue("Didn't get JSON representation", storage.toJSON() instanceof java.lang.String);
	}

	@Test
	public final void testToString() {
		assertTrue("Didn't get string", storage.toString() instanceof java.lang.String);
	}


	@Test
	public final void testType() {
		assertTrue("Bad type",storage.type().equals(type));
	}

	@Test
	public final void testCapacity() {
		assertTrue("Bad capacity",storage.capacity()==capacity);
	}

	@Test
	public final void testAvailable() {
		assertTrue("Bad available",storage.available()==available);
	}

}
