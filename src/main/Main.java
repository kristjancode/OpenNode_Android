package main;

import core.Core;
import core.config.Config;
import core.interfaces.*;
import core.models.*;

public class Main 
{
	public static void main(String[] args)
	{
		Config.SERVER_HOSTNAME = "127.0.0.1";
		Config.SERVER_PORT = 8080;
		Core core = new Core();
		
		ComputeManager computeManager = core.computeManager();	
		NetworkManager networkManager = core.networkManager();
		StorageManager storageManager = core.storageManager();
		TemplateManager templateManager = core.templateManager();
		NewsManager newsManager = core.newsManager();
		testManager((AbstractManager) computeManager);
		testManager((AbstractManager) networkManager);
		testManager((AbstractManager) storageManager);
		testManager((AbstractManager) templateManager);
		testManager((AbstractManager) newsManager);
		
		testComputeCreate(computeManager); //CREATES A NEW COMPUTE
		testComputeUpdate(computeManager); //UPDATES AN EXISTING COMPUTE
	}
	
	private static void testManager(AbstractManager<Item> manager)
	{
		if (manager.load())
		{
			int itemCount = manager.count();
			System.out.println("Successfully loaded items. Count: " + itemCount + ".");
			System.out.println("Obtaining details for first 10 items.");
			
			for (int i = 0; i < itemCount && i < 10; i++)
			{
				Item item = manager.item(i);
				System.out.print("Requesting details for " + item.toString());
				
				if (manager.details(item))
				{
					System.out.println(" -> " + item.toString());
				}
				else
				{
					System.out.println(" -> failed");
				}
			}
		}
		else
		{
			System.out.println("Failed to load items.");
		}
		
		System.out.println("");
	}
	
	private static void testComputeCreate(ComputeManager computeManager)
	{
		System.out.println("Item count before creating: " + computeManager.count());
		Compute compute = new Compute(0, "nameeeeee", "archhhhhhhhh", 4, 40.1F, 90, "stateeeeeeeee", "templateeeeeeeeee");
		
		if (computeManager.create(compute))
		{
			System.out.println("New item count: " + computeManager.count());
		}
		else
		{
			System.out.println("Failed.");
		}
	}
	
	private static void testComputeUpdate(ComputeManager computeManager)
	{
		Compute c = computeManager.item(0);
		Compute updated = new Compute(c.id(), "LOOK AT THIS", c.arch(), c.cores(), c.cpu(), c.memory(), c.state(), c.template());
		
		if (computeManager.update(c, updated))
		{
			System.out.println("Updated: " + c.toString());
		}
		else
		{
			System.out.println("Failed.");
		}
	}
}
