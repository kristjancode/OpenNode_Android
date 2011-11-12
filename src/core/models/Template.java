package core.models;

import org.json.JSONObject;

public class Template extends Item
{
	public Template()
	{
		super();
		m_minDiskSize = 0;
		m_minMemorySize = 0;
		m_minCpu = 0;
		m_maxDiskSize = 0;
		m_maxMemorySize = 0;
		m_maxCpu = 0;
	}
	
	public Template(int id, String name, int minDiskSize, int minMemorySize, int minCpu, int maxDiskSize, int maxMemorySize, int maxCpu)
	{
		super(id, name);
		m_minDiskSize = minDiskSize;
		m_minMemorySize = minMemorySize;
		m_minCpu = minCpu;
		m_maxDiskSize = maxDiskSize;
		m_maxMemorySize = maxMemorySize;
		m_maxCpu = maxCpu;
	}
	
	public int minDiskSize()
	{
		return m_minDiskSize;
	}
	
	public int minMemorySize()
	{
		return m_minMemorySize;
	}
	
	public int minCpu()
	{
		return m_minCpu;
	}
	
	public int maxDiskSize()
	{
		return m_maxDiskSize;
	}
	
	public int maxMemorySize()
	{
		return m_maxMemorySize;
	}
	
	public int maxCpu()
	{
		return m_maxCpu;
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
	
	public boolean assign(JSONObject jsonObject)
	{
		boolean success = false;
		
		try
		{
			Template temp = new Template();
			temp.assignTemplate(jsonObject);
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
			valid = valid ? m_minCpu >= 0 : false;
			valid = valid ? m_maxDiskSize >= 0 : false;
			valid = valid ? m_maxMemorySize >= 0 : false;
			valid = valid ? m_maxCpu >= 0 : false;
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
		stringRepresentation += m_name + ", ";
		stringRepresentation += m_minDiskSize + ", ";
		stringRepresentation += m_minMemorySize + ", ";
		stringRepresentation += m_minCpu + ", ";
		stringRepresentation += m_maxDiskSize + ", ";
		stringRepresentation += m_maxMemorySize + ", ";
		stringRepresentation += m_maxCpu;
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	private void assignTemplate(Template template)
	{
		assignItem(template);
		m_minDiskSize = template.m_minDiskSize;
		m_minMemorySize = template.m_minMemorySize;
		m_minCpu = template.m_minCpu;
		m_maxDiskSize = template.m_maxDiskSize;
		m_maxMemorySize = template.m_maxMemorySize;
		m_maxCpu = template.m_maxCpu;
	}
	
	private void assignTemplate(JSONObject jsonObject) throws Exception
	{
		assignItem(jsonObject);
		m_minDiskSize = jsonObject.getInt("min_disk_size");
		m_minMemorySize = jsonObject.getInt("min_memory_size");
		m_minCpu = jsonObject.getInt("min_cpu");
		m_maxDiskSize = jsonObject.getInt("max_disk_size");
		m_maxMemorySize = jsonObject.getInt("max_memory_size");
		m_maxCpu = jsonObject.getInt("max_cpu");
	}
	
	private void jsonTemplate(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("min_disk_size", m_minDiskSize);
		jsonObject.put("min_memory_size", m_minMemorySize);
		jsonObject.put("min_cpu", m_minCpu);
		jsonObject.put("max_disk_size", m_maxDiskSize);
		jsonObject.put("max_memory_size", m_maxMemorySize);
		jsonObject.put("max_cpu", m_maxCpu);
	}
	
	private int m_minDiskSize;
	private int m_minMemorySize;
	private int m_minCpu;
	private int m_maxDiskSize;
	private int m_maxMemorySize;
	private int m_maxCpu;
}
