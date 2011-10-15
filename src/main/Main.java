package main;

import java.util.Scanner;
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
		
		testManagers(core);
		testItemToJSON();
		testComputeCreateUpdateDelete(core.computeManager());
	}
	
	private static void testManagers(Core core)
	{
		System.out.println("Testing item loading and item details obtaining capabilities of all managers.");
		System.out.println("Press enter to continue...");
		new Scanner(System.in).nextLine();
		
		ComputeManager computeManager = core.computeManager();
		NetworkManager networkManager = core.networkManager();
		StorageManager storageManager = core.storageManager();
		TemplateManager templateManager = core.templateManager();
		NewsManager newsManager = core.newsManager();
		
		loadAndGetDetails((AbstractManager) computeManager);
		loadAndGetDetails((AbstractManager) networkManager);
		loadAndGetDetails((AbstractManager) storageManager);
		loadAndGetDetails((AbstractManager) templateManager);
		loadAndGetDetails((AbstractManager) newsManager);
		
		System.out.println("");
	}
	
	private static void testItemToJSON()
	{
		System.out.println("Testing toJSON() method of all items.");
		System.out.println("Press enter to continue...");
		new Scanner(System.in).nextLine();
		
		Compute compute = new Compute(0, "c1", "c2", 20, 30, 40, "c3", "c4");
		Network network = new Network(1, "n1", "n2", "n3", "n4", "n5");
		Storage storage = new Storage(2, "s1", 20, "s2");
		Template template = new Template(3, "t1", 20, 30);
		News news = new News(4, "n1", "n2", "n3");
		
		System.out.println("Compute toJSON() -> " + compute.toJSON());
		System.out.println("Network toJSON() -> " + network.toJSON());
		System.out.println("Storage toJSON() -> " + storage.toJSON());
		System.out.println("Template toJSON() -> " + template.toJSON());
		System.out.println("News toJSON() -> " + news.toJSON());
		
		System.out.println("");
	}
	
	private static void loadAndGetDetails(AbstractManager<Item> manager)
	{
		if (manager.load())
		{
			int itemCount = manager.count();
			System.out.println("Successfully loaded items. Count: " + itemCount + ".");
			System.out.println("Obtaining details for the first 4 items.");
			
			for (int i = 0; i < itemCount && i < 4; i++)
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
	
	private static void testComputeCreateUpdateDelete(ComputeManager computeManager)
	{
		System.out.println("Testing creation/updating/deleting of a Compute item.");
		System.out.println("Press enter to continue...");
		new Scanner(System.in).nextLine();
		
		Compute compute1 = new Compute(0, "c1", "c2", 20, 30, 40, "c3", "c4");
		Compute compute2 = new Compute(0, "ccccc1", "c2", 20, 30, 40, "c3", "c4");
		
		boolean success = computeManager.load(); //refresh
		System.out.println("Count before creation: " + computeManager.count());
		success = success ? computeManager.create(compute1) : false; //create compute1 in server
		System.out.println("Count after creation: " + computeManager.count());
		success = success ? computeManager.update(compute1, compute2) : false; //update compute1 with the contents of compute2 in server
		System.out.println("Count after updating: " + computeManager.count());
		success = success ? computeManager.delete(compute1) : false; //delete compute1 from server
		System.out.println("Count after deletion: " + computeManager.count());
		
		System.out.println("Success: " + success);
	}
}
