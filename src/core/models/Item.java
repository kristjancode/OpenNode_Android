package core.models;

import org.json.JSONObject;

public abstract class Item 
{
	protected Item()
	{
		m_id = 0;
		m_name = null;
	}
	
	protected Item(int id, String name)
	{
		m_id = id;
		m_name = name;
	}
	
	public int id()
	{
		return m_id;
	}
	
	public String name()
	{
		return m_name;
	}
	
	public boolean valid()
	{
		boolean valid = m_id >= 0;
		valid = valid ? m_name != null : false;
		
		return valid;
	}
	
	public abstract boolean assign(Item item);
	public abstract boolean assign(JSONObject jsonObject, boolean full);
	public abstract String toJSON();
	public abstract String toString();
	
	protected void assignItem(Item item)
	{
		m_id = item.m_id;
		m_name = item.m_name;
	}
	
	protected void assignItem(JSONObject jsonObject, boolean full) throws Exception
	{
		m_id = jsonObject.getInt("id");
		m_name = jsonObject.getString("name");
	}
	
	protected void jsonItem(JSONObject jsonObject) throws Exception
	{	
		jsonObject.put("id", m_id);
		jsonObject.put("name", m_name);
	}
	
	protected int m_id;
	protected String m_name;
}
