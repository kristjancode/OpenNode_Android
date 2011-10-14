package core.models;

import org.json.JSONObject;

public class Template extends Item
{
	public Template()
	{
		
	}
	
	public int minDiskSize()
	{
		return m_minDiskSize;
	}
	
	public int minMemorySize()
	{
		return m_minMemorySize;
	}
	
	public boolean assign(Item item)
	{
		boolean success = false;
		
		try
		{
			assignTemplate((Template) item);
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
			Template temp = new Template();
			temp.assignTemplate(jsonObject, full);
			assignTemplate(temp);
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
		String stringRepresentation = "Template(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		
		if (m_full)
		{
			stringRepresentation += ", " + m_minDiskSize + ", ";
			stringRepresentation += m_minMemorySize;
		}
		
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	private void assignTemplate(Template template)
	{
		assignItem(template);
		m_minDiskSize = template.m_minDiskSize;
		m_minMemorySize = template.m_minMemorySize;
	}
	
	private void assignTemplate(JSONObject jsonObject, boolean full) throws Exception
	{
		assignItem(jsonObject, full);
		
		if (m_full)
		{
			m_id = jsonObject.getInt("id");
			m_name = jsonObject.getString("name");
			m_minDiskSize = jsonObject.getInt("min_disk_size");
			m_minMemorySize = jsonObject.getInt("min_memory_size");
		}
	}
	
	private int m_minDiskSize;
	private int m_minMemorySize;
}
