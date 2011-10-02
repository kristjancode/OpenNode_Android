package core.models;

import org.json.JSONObject;

public class Template extends Item
{
	private Template()
	{
		
	}
	
	private void assign(Template template)
	{
		super.assign(template);
		m_minDiskSize = template.m_minDiskSize;
		m_minMemorySize = template.m_minMemorySize;
	}
	
	public int minDiskSize()
	{
		return m_minDiskSize;
	}
	
	public int minMemorySize()
	{
		return m_minMemorySize;
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
	
	public static Template parse(JSONObject jsonObject, boolean full)
	{
		Template template = null;
		Template temp = new Template();
		
		if (parse(temp, jsonObject, full))
		{
			template = temp;
		}
		
		return template;
	}
	
	public static boolean parse(Template template, JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			Template temp = new Template();
			temp.m_full = full;
			
			if (full)
			{
				temp.m_id = jsonObject.getInt("id");
				temp.m_name = jsonObject.getString("name");
				temp.m_minDiskSize = jsonObject.getInt("min_disk_size");
				temp.m_minMemorySize = jsonObject.getInt("min_memory_size");
			}
			else
			{
				String key = (String) jsonObject.keys().next();
				temp.m_id = Integer.parseInt(key);
				temp.m_name = jsonObject.getString(key);
			}
			
			template.assign(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private int m_minDiskSize;
	private int m_minMemorySize;
}
