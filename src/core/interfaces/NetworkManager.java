package core.interfaces;

import org.json.JSONObject;
import core.Core;
import core.models.Network;

public class NetworkManager extends AbstractManager<Network>
{
	public NetworkManager(Core core)
	{
		super(core, "networks");
	}
	
	@Override
	protected Network parseItem(JSONObject jsonObject, boolean full)
	{
		return Network.parse(jsonObject, full);
	}
	
	@Override
	protected boolean parseItem(Network compute, JSONObject jsonObject, boolean full)
	{
		return Network.parse(compute, jsonObject, full);
	}
}
