package core.interfaces;

import static org.junit.Assert.*;
import org.junit.*;

import core.Core;
import core.models.Template;

public class TemplateManagerTest {

	Core core;
	TemplateManager templateManager;
	
	@Before
	public void setUp() throws Exception {
		core = new Core();
		core.config().serverHostname("localhost");
		core.config().serverPort(8080);
		core.config().serverUsername("opennode");
		core.config().serverPassword("demo");
		core.serverConnector().authenticate();
		templateManager = core.templateManager();
	}
	
	@Test
	public void testTemplateManager() {
		assertNotNull(templateManager);
	}
	
	@Test
	public void testNegativeIndexItem() {
		assertTrue("Failed to load",templateManager.load());
		assertNull("There shouldn't be anything on negative index",templateManager.item(-1));
	}
	
	@Test
	public void testCount(){
		assertTrue("Failed to load",templateManager.load());
		assertTrue("Should count some items",templateManager.count()>0);
	}
	
	@Test
	public void testDetails() {
		assertTrue("Failed to load template",templateManager.load());
		assertTrue("Failed to get details",templateManager.details(templateManager.item(0)));
	}
	
	//@Test
	//If this fails, it's probably server side
	public void testCreateWithSpecial() {
		//Create new template with special characters
		Template template = new Template(0,"Nårska täcken",1,1,1,2,2,2);
		//Try to add it
		assertTrue("Failed to add Template",templateManager.create(template));
		//Try to get it back (assume it is last element)
		assertEquals("Either template wasn't last item or something is broken",template.name(),templateManager.item(templateManager.count()-1).name());
	}
	
	@Test
	public void testWithNullString(){
		Template template = new Template(0,null,1,1,1,2,2,2);
		assertFalse("Should not be able to make item with nullstring.",templateManager.create(template));
	}

	@Test
	public void testCreateEmptyItem(){
		Template template = new Template();
		//Should not be able do add empty item
		assertFalse(templateManager.create(template));
	}
	
	@Test
	public void testCreateItem(){
		assertTrue("Failed to load",templateManager.load());
		int count = templateManager.count();
		Template template = new Template(0,"template",1,1,1,2,2,2);
		assertTrue("Invalid template",template.valid());
		assertTrue("Should be able to create damn template",templateManager.create(template));
		assertTrue("Failed to load second time",templateManager.load());
		assertEquals("New item not counted",templateManager.count(),count+1);
	}
	
	@Test
	public void testDelete() {
		Template template = new Template();
		assertFalse(templateManager.delete(template));
	}

}
