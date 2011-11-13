package core.models;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkTest {
	Network network;
	int id = 0;
	String name = "name";
	String ip = "0.0.0.0";
	String mask = "255.255.255.255";
	String allocation = "addressAllocation";
	String gateway= "gateway";

	@Before
	public void setUp() throws Exception {
		network = new Network(0,name,ip,mask,allocation,gateway);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testValid() {
		Network invalid_network = new Network(0,"name",null,null,null,null);
		assertFalse("This item should be invalid",invalid_network.valid());
	}
	
	@Test
	public final void testAssignItem() {
		Item item = null;
		assertFalse("Assigning null object to network should not work",network.assign(item));
	}

	@Test
	public final void testAssignJSONObject() {
		Network duplicate = new Network();
		try {
			String JSONRepresentation = network.toJSON();
			JSONObject jsonO = new JSONObject(JSONRepresentation);
			duplicate.assign(jsonO);
			assertTrue("Representation didn't come out the same, assign worked tho", duplicate.toJSON().equals(JSONRepresentation));
		} catch (Exception exception) {
			fail("Something went terribly wrong: "+exception.toString());
		}
	}

	@Test
	public final void testToJSON() {
		assertTrue("Didn't get JSON representation", network.toJSON() instanceof java.lang.String);
	}

	@Test
	public final void testToString() {
		assertTrue("Didn't get string", network.toString() instanceof java.lang.String);
	}

	@Test
	public final void testNetwork() {
		Network networkobject = new Network();
		assertTrue("Got null object instead",networkobject!=null);
	}

	@Test
	public final void testIp() {
		assertTrue("Bad mask",network.ip().equals(ip));
	}

	@Test
	public final void testMask() {
		assertTrue("Bad mask",network.mask().equals(mask));
	}

	@Test
	public final void testAddressAllocation() {
		assertTrue("Bad addressAllocation",network.addressAllocation().equals(allocation));
	}

	@Test
	public final void testGateway() {
		assertTrue("Bad gateway",network.gateway().equals(gateway));
	}

}
