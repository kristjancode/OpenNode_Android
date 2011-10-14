package core.models;

import org.json.JSONObject;

public class Storage extends Item
{
	public Storage()
	{
		m_size = 0;
		m_type = null;
	}
	
	public int size()
	{
		return m_size;
	}
	
	public String type()
	{
		return m_type;
	}
	
	public boolean assign(Item item)
	{
		boolean success = false;
		
		try
		{
			assignStorage((Storage) item);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	public boolean assign(JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			Storage temp = new Storage();
			temp.assignStorage(jsonObject, full);
			assignStorage(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	@Override
	public String toJSON()
	{
		String jsonString = null;
		
		try
		{
			
		}
		catch (Exception exception)
		{
			
		}
		
		return jsonString;
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
	
	private void assignStorage(Storage storage)
	{
		assignItem(storage);
		m_size = storage.m_size;
		m_type = storage.m_type;
	}
	
	private void assignStorage(JSONObject jsonObject, boolean full) throws Exception
	{
		assignItem(jsonObject, full);
		
		if (m_full)
		{
			m_size = jsonObject.getInt("size");
			m_type = jsonObject.getString("type");
		}
	}
	
	private int m_size;
	private String m_type;
}
