package core.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testValid() {
		Network network = new Network(0,"name",null,null,null,null);
		assertFalse("This item should be invalid",network.valid());
	}

	@Test
	public final void testAssignItem() {
		Network network = new Network();
		Item item = null;
		assertFalse("Assigning null object to network should not work",network.assign(item));
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
	public final void testNetwork() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNetworkIntStringStringStringStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIp() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testMask() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testAddressAllocation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGateway() {
		fail("Not yet implemented"); // TODO
	}

}
