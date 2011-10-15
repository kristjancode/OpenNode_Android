package core.models;

import org.json.JSONObject;

public class Storage extends Item
{
	public Storage()
	{
		super();
		m_size = 0;
		m_type = null;
	}
	
	public Storage(int id, String name, int size, String type)
	{
		super(id, name);
		m_size = size;
		m_type = type;
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
		String jsonRepresentation = null;
		
		try
		{
			JSONObject jsonObject = new JSONObject();
			jsonStorage(jsonObject);
			jsonRepresentation = jsonObject.toString();
		}
		catch (Exception exception)
		{
			
		}
		
		return jsonRepresentation;
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
	
	private void jsonStorage(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("size", m_size);
		jsonObject.put("type", m_type);
	}
	
	private int m_size;
	private String m_type;
}
