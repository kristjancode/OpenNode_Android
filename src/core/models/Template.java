package core.models;

import org.json.JSONObject;

public class Template extends Item
{
	public Template()
	{
		super();
		m_minDiskSize = 0;
		m_minMemorySize = 0;
	}
	
	public Template(int id, String name, int minDiskSize, int minMemorySize)
	{
		super(id, name);
		m_minDiskSize = minDiskSize;
		m_minMemorySize = minMemorySize;
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
	public boolean valid()
	{
		boolean valid = false;
		
		try
		{
			valid = super.valid();
			valid = valid ? m_minDiskSize >= 0 : false;
			valid = valid ? m_minMemorySize >= 0 : false;
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
			jsonTemplate(jsonObject);
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
		String stringRepresentation = "Template(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		stringRepresentation += ", " + m_minDiskSize + ", ";
		stringRepresentation += m_minMemorySize;
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
		m_id = jsonObject.getInt("id");
		m_name = jsonObject.getString("name");
		m_minDiskSize = jsonObject.getInt("min_disk_size");
		m_minMemorySize = jsonObject.getInt("min_memory_size");
	}
	
	private void jsonTemplate(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("min_disk_size", m_minDiskSize);
		jsonObject.put("min_memory_size", m_minMemorySize);
	}
	
	private int m_minDiskSize;
	private int m_minMemorySize;
}
