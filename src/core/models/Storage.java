package core.models;

import org.json.JSONObject;

public class Storage extends Item
{
	public Storage()
	{
		super();
		m_type = null;
		m_capacity = 0;
		m_available = 0;
	}
	
	public Storage(int id, String name, String type, int capacity, int available)
	{
		super(id, name);
		m_type = type;
		m_capacity = capacity;
		m_available = available;
	}
	
	public String type()
	{
		return m_type;
	}
	
	public int capacity()
	{
		return m_capacity;
	}
	
	public int available()
	{
		return m_available;
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
	
	public boolean assign(JSONObject jsonObject)
	{
		boolean success = false;
		
		try
		{
			Storage temp = new Storage();
			temp.assignStorage(jsonObject);
			assignStorage(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	@Override
	public boolean valid()
	{
		boolean valid = false;
		
		try
		{
			valid = super.valid();
			valid = valid ? m_type != null : false;
			valid = valid ? m_capacity >= 0 : false;
			valid = valid ? m_available >= 0 : false;
		}
		catch (Exception exception)
		{
			
		}
		
		return valid;
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
		stringRepresentation += m_name + ", ";
		stringRepresentation += m_type + ", ";	
		stringRepresentation += m_capacity + ", ";
		stringRepresentation += m_available;
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	private void assignStorage(Storage storage)
	{
		assignItem(storage);
		m_type = storage.m_type;
		m_capacity = storage.m_capacity;
		m_available = storage.m_available;
	}
	
	private void assignStorage(JSONObject jsonObject) throws Exception
	{
		assignItem(jsonObject);
		m_type = jsonObject.getString("type");
		m_capacity = jsonObject.getInt("capacity");
		m_available = jsonObject.getInt("available");
	}
	
	private void jsonStorage(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("type", m_type);
		jsonObject.put("capacity", m_capacity);
		jsonObject.put("available", m_available);
	}
	
	private String m_type;
	private int m_capacity;
	private int m_available;
}
