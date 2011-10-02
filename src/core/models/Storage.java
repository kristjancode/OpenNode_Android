package core.models;

import org.json.JSONObject;

public class Storage extends Item
{
	private Storage()
	{
		m_size = 0;
		m_type = null;
	}
	
	private void assign(Storage storage)
	{
		super.assign(storage);
		m_size = storage.m_size;
		m_type = storage.m_type;
	}
	
	public int size()
	{
		return m_size;
	}
	
	public String type()
	{
		return m_type;
	}
	
	@Override
	public String toString()
	{
		String stringRepresentation = "Storage(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		
		if (m_full)
		{
			stringRepresentation += ", " + m_size + ", ";
			stringRepresentation += m_type;
		}
		
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	public static Storage parse(JSONObject jsonObject, boolean full)
	{
		Storage storage = null;
		Storage temp = new Storage();
		
		if (parse(temp, jsonObject, full))
		{
			storage = temp;
		}
		
		return storage;
	}
	
	public static boolean parse(Storage storage, JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			Storage temp = new Storage();
			temp.m_full = full;
			
			if (full)
			{
				temp.m_id = jsonObject.getInt("id");
				temp.m_name = jsonObject.getString("name");
				temp.m_size = jsonObject.getInt("size");
				temp.m_type = jsonObject.getString("type");
			}
			else
			{
				String key = (String) jsonObject.keys().next();
				temp.m_id = Integer.parseInt(key);
				temp.m_name = jsonObject.getString(key);
			}
			
			storage.assign(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private int m_size;
	private String m_type;
}
