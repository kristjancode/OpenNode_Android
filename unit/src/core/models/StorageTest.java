package core.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
	Storage storage;

	@Before
	public void setUp() throws Exception {
		storage = new Storage(0,"storagename","storagetype",0,0);
	}

	@After
	public void tearDown() throws Exception {
		storage=null;
	}

	@Test
	public final void testValid() {
		assertTrue("This storage item should be valid",storage.valid());
	}

	@Test
	public final void testAssignItem() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAssignJSONObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToJSON() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testStorageIntStringStringIntInt() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testCapacity() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAvailable() {
		fail("Not yet implemented"); // TODO
	}

}
