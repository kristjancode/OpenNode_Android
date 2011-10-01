package core.interfaces;

import org.json.JSONObject;
import core.Core;
import core.models.Compute;

public class ComputeManager extends AbstractManager<Compute>
{
	public ComputeManager(Core core)
	{
		super(core, "computes");
	}
	
	@Override
	protected Compute parseItem(JSONObject jsonObject, boolean full)
	{
		return Compute.parse(jsonObject, full);
	}
	
	@Override
	protected boolean parseItem(Compute compute, JSONObject jsonObject, boolean full)
	{
		return Compute.parse(compute, jsonObject, full);
	}
}
