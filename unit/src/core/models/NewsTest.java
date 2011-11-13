package core.models;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewsTest {
	News news;
	int id = 0;
	String name = "name";
	String type = "type";
	String content = "content";

	@Before
	public void setUp() throws Exception {
		news = new News(id,name,type,content);
	}

	@After
	public void tearDown() throws Exception {
		news=null;
	}

	@Test
	public final void testValid() {
		assertTrue("This news item should be valid",news.valid());
		//also make one that is invalid
		News invalid_news = new News(0,null,null,null);
		assertFalse("This news item should be invalid",invalid_news.valid());
	}

	@Test
	public final void testAssignItem() {
		Item item = null;
		assertFalse("Assigning null object to network should not work",news.assign(item));
	}

	@Test
	public final void testAssignJSONObject() {
		News duplicate = new News();
		try {
			String JSONRepresentation = news.toJSON();
			JSONObject jsonO = new JSONObject(JSONRepresentation);
			duplicate.assign(jsonO);
			assertTrue("Representation didn't come out the same, assign worked tho", duplicate.toJSON().equals(JSONRepresentation));
		} catch (Exception exception) {
			fail("Something went terribly wrong: "+exception.toString());
		}
	}

	@Test
	public final void testToJSON() {
		assertTrue("Didn't get JSON representation", news.toJSON() instanceof java.lang.String);
	}

	@Test
	public final void testToString() {
		assertTrue("Didn't get string", news.toString() instanceof java.lang.String);
	}


	@Test
	public final void testType() {
		assertTrue("Bad type",news.type().equals(type));
	}

	@Test
	public final void testContent() {
		assertTrue("Bad capacity",news.content().equals(content));
	}

}
