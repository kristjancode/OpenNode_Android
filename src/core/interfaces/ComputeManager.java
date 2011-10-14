package core.interfaces;

import core.Core;
import core.models.Compute;

public class ComputeManager extends AbstractManager<Compute>
{
	public ComputeManager(Core core)
	{
		super(core, "computes");
	}
	
	@Override
	protected Compute itemInstance()
	{
		return new Compute();
	}
}
