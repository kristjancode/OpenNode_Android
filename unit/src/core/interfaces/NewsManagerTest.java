package core.interfaces;

import static org.junit.Assert.*;
import org.junit.*;

import core.Core;
import core.models.News;

public class NewsManagerTest {

	Core core;
	NewsManager newsManager;
	
	@Before
	public void setUp() throws Exception {
		core = new Core();
		core.config().serverHostname("localhost");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		newsManager = core.newsManager();
	}
	
	@Test
	public void testNewsManager() {
		assertNotNull(newsManager);
	}
	
	@Test
	public void testNegativeIndexItem() {
		assertTrue("Failed to load",newsManager.load());
		assertNull("There shouldn't be anything on negative index",newsManager.item(-1));
	}
	
	@Test
	public void testCount(){
		assertTrue("Failed to load",newsManager.load());
		assertTrue("Should count some items",newsManager.count()>0);
	}
	
	@Test
	public void testDetails() {
		assertTrue("Failed to load",newsManager.load());
	}
	
	//@Test
	//If this fails, it's probably server side
	public void testCreateWithSpecial() {
		assertTrue("Failed to load",newsManager.load());
		//Create new news with special characters
		News news = new News(Integer.MAX_VALUE, "Nårska täcken", "type", "content");
		//Try to add it
		assertTrue("Failed to add News",newsManager.create(news));
		//Try to get it back (assume it is last element)
		assertEquals("Either news wasn't last item or something is broken",news.name(),newsManager.item(newsManager.count()-1).name());
	}
	
	@Test
	public void testWithNullString(){
		assertTrue("Failed to load",newsManager.load());
		News news = new News(Integer.MAX_VALUE, null, "type", "content");
		assertFalse("Should not be able to make item with nullstring.",newsManager.create(news));
		//assertEquals("Either news wasn't last item or something is broken",news.name(),newsManager.item(newsManager.count()-1).name());
	}

	@Test
	public void testCreateEmptyItem(){
		assertTrue("Failed to load",newsManager.load());
		News news = new News();
		//Should not be able do add empty item
		assertFalse(newsManager.create(news));
	}
	
	@Test
	public void testCreateItem(){
		assertTrue("Failed to load",newsManager.load());
		int count = newsManager.count();
		News news = new News(0,"name", "type", "content");
		assertTrue("Invalid news",news.valid());
		assertTrue("Should be able to create damn news",newsManager.create(news));
		assertTrue("Failed to load second time",newsManager.load());
		assertEquals("New item not counted",newsManager.count(),count+1);
	}
	
	@Test
	public void testDelete() {
		News news = new News();
		assertFalse(newsManager.delete(news));
	}

}
