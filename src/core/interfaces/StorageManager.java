package core.interfaces;

import core.Core;
import core.models.Storage;

public class StorageManager extends AbstractManager<Storage>
{
	public StorageManager(Core core)
	{
		super(core, "storages");
	}
	
	@Override
	protected Storage itemInstance()
	{
		return new Storage();
	}
}
