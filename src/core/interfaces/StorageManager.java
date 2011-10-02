package core.interfaces;

import org.json.JSONObject;
import core.Core;
import core.models.Storage;

public class StorageManager extends AbstractManager<Storage>
{
	public StorageManager(Core core)
	{
		super(core, "storages");
	}
	
	@Override
	protected Storage parseItem(JSONObject jsonObject, boolean full)
	{
		return Storage.parse(jsonObject, full);
	}
	
	@Override
	protected boolean parseItem(Storage storage, JSONObject jsonObject, boolean full)
	{
		return Storage.parse(storage, jsonObject, full);
	}
}
