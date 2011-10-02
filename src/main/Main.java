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
	}
	
	private static void testManager(AbstractManager<Item> manager)
	{
		if (manager.loadItems())
		{
			int itemCount = manager.itemCount();
			System.out.println("Successfully loaded items. Count: " + itemCount + ".");
			System.out.println("Obtaining details for " + itemCount + " items.");
			
			for (int i = 0; i < itemCount; i++)
			{
				Item item = manager.item(i);
				System.out.print("Requesting details for " + item.toString());
				
				if (manager.loadItemDetails(item))
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
	
	private static void testComputeManager(ComputeManager computeManager)
	{
		if (computeManager.loadItems())
		{
			System.out.println("Successfully loaded computes. Count: " + computeManager.itemCount() + ".");
			System.out.println("Obtaining details for first 4 computes.");
			int computeCount = computeManager.itemCount();
			
			for (int i = 0; i < 4 && i < computeCount; i++)
			{
				Compute compute = computeManager.item(i);
				System.out.print("Attempting to request details for: " + compute.toString() + " -> ");
				
				if (computeManager.loadItemDetails(compute))
				{
					System.out.println(compute.toString());
				}
				else
				{
					System.out.println("Failed to load details.");
				}
			}	
		}
		else
		{
			System.out.println("Failed to load computes.");
		}
	}
	
	private static void testNetworkManager(NetworkManager networkManager)
	{
		if (networkManager.loadItems())
		{
			System.out.println("Successfully loaded networks. Count: " + networkManager.itemCount() + ".");
			System.out.println("Obtaining details for first 4 networks.");
			int computeCount = networkManager.itemCount();
			
			for (int i = 0; i < 4 && i < computeCount; i++)
			{
				Network network = networkManager.item(i);
				System.out.print("Attempting to request details for: " + network.toString() + " -> ");
				
				if (networkManager.loadItemDetails(network))
				{
					System.out.println(network.toString());
				}
				else
				{
					System.out.println("Failed to load details.");
				}
			}	
		}
		else
		{
			System.out.println("Failed to load networks.");
		}
	}
}
