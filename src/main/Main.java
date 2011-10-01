package main;

import core.Core;
import core.interfaces.ComputeManager;
import core.interfaces.NetworkManager;
import core.models.Compute;
import core.models.Network;

public class Main 
{
	public static void main(String[] args)
	{
		Core core = new Core();
		
		ComputeManager computeManager = core.computeManager();
		testComputeManager(computeManager);
		
		NetworkManager networkManager = core.networkManager();
		testNetworkManager(networkManager);
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
			System.out.println("Failed to load computes.");
		}
	}
}
