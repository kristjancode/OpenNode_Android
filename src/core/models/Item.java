package core.models;

import org.json.JSONObject;

public abstract class Item 
{
	protected Item()
	{
		m_id = 0;
		m_name = null;
		m_full = false;
	}
	
	protected Item(int id, String name)
	{
		m_id = id;
		m_name = name;
		m_full = true;
	}
	
	public int id()
	{
		return m_id;
	}
	
	public String name()
	{
		return m_name;
	}
	
	public boolean full()
	{
		return m_full;
	}
	
	public abstract boolean assign(Item item);
	public abstract boolean assign(JSONObject jsonObject, boolean full);
	public abstract String toJSON();
	public abstract String toString();
	
	protected void assignItem(Item item)
	{
		m_id = item.m_id;
		m_name = item.m_name;
		m_full = item.m_full;
	}
	
	protected void assignItem(JSONObject jsonObject, boolean full) throws Exception
	{
		if ((m_full = full))
		{
			m_id = jsonObject.getInt("id");
			m_name = jsonObject.getString("name");
		}
		else
		{
			String key = (String) jsonObject.keys().next();
			m_id = Integer.parseInt(key);
			m_name = jsonObject.getString(key);
		}
	}
	
	protected void jsonItem(JSONObject jsonObject) throws Exception
	{	
		jsonObject.put("id", m_id);
		jsonObject.put("name", m_name);
	}
	
	protected int m_id;
	protected String m_name;
	protected boolean m_full;
}
