package core.interfaces;

import core.Core;
import core.models.Network;

public class NetworkManager extends AbstractManager<Network>
{
	public NetworkManager(Core core)
	{
		super(core, "networks");
	}
	
	@Override
	protected Network itemInstance()
	{
		return new Network();
	}
}
