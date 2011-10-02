package core.models;

import org.json.JSONObject;

public final class Compute extends Item
{
	private Compute()
	{
		super();
	}
	
	private void assign(Compute compute)
	{
		super.assign(compute);
		m_arch = compute.m_arch;
		m_cpu = compute.m_cpu;
		m_cores = compute.m_cores;
		m_memory = compute.m_memory;
		m_state = compute.m_state;
		m_template = compute.m_template;
	}
	
	public String hostname()
	{
		return m_name;
	}
	
	public String arch()
	{
		return m_arch;
	}
	
	public float cpu()
	{
		return m_cpu;
	}
	
	public int cores()
	{
		return m_cores;
	}
	
	public int memory()
	{
		return m_memory;
	}
	
	public String state()
	{
		return m_state;
	}
	
	public String template()
	{
		return m_template;
	}
	
	@Override
	public String toString()
	{
		String stringRepresentation = "Compute (";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		
		if (m_full)
		{
			stringRepresentation += ", " + m_arch + ", ";
			stringRepresentation += m_cores + ", ";
			stringRepresentation += m_cpu + ", ";
			stringRepresentation += m_memory + ", ";
			stringRepresentation += m_state + ", ";
			stringRepresentation += m_template;
		}
		
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	public static Compute parse(JSONObject jsonObject, boolean full)
	{
		Compute compute = null;
		Compute temp = new Compute();
		
		if (parse(temp, jsonObject, full))
		{
			compute = temp;
		}
		
		return compute;
	}
	
	public static boolean parse(Compute compute, JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			Compute temp = new Compute();
			temp.m_full = full;
			
			if (full)
			{
				temp.m_id = jsonObject.getInt("id");
				temp.m_name = jsonObject.getString("hostname");
				temp.m_arch = jsonObject.getString("arch");
				temp.m_cores = jsonObject.getInt("cores");
				temp.m_cpu = (float) jsonObject.getDouble("cpu");
				temp.m_memory = jsonObject.getInt("memory");
				temp.m_state = jsonObject.getString("state");
				temp.m_template = jsonObject.getString("template");
			}
			else
			{
				String key = (String) jsonObject.keys().next();
				temp.m_id = Integer.parseInt(key);
				temp.m_name = jsonObject.getString(key);
			}
			
			compute.assign(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private String m_arch;
	private int m_cores;
	private float m_cpu;
	private int m_memory;
	private String m_state;
	private String m_template;
}
