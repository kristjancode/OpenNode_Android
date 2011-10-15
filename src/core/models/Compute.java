package core.models;

import org.json.JSONObject;

public final class Compute extends Item
{
	public Compute()
	{
		super();
		m_arch = null;
		m_cores = 0;
		m_cpu = 0;
		m_memory = 0;
		m_state = null;
		m_template = null;
	}
	
	public Compute(int id, String name, String arch, int cores, float cpu, int memory, String state, String template)
	{
		super(id, name);
		m_arch = arch;
		m_cores = cores;
		m_cpu = cpu;
		m_memory = memory;
		m_state = state;
		m_template = template;
	}
	
	public String arch()
	{
		return m_arch;
	}
	
	public int cores()
	{
		return m_cores;
	}
	
	public float cpu()
	{
		return m_cpu;
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
	
	public boolean assign(Item item)
	{
		boolean success = false;
		
		try
		{
			assignCompute((Compute) item);
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
			Compute temp = new Compute();
			temp.assignCompute(jsonObject, full);
			assignCompute(temp);
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
			jsonCompute(jsonObject);
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
	
	private void assignCompute(Compute compute)
	{
		assignItem(compute);
		m_arch = compute.m_arch;
		m_cpu = compute.m_cpu;
		m_cores = compute.m_cores;
		m_memory = compute.m_memory;
		m_state = compute.m_state;
		m_template = compute.m_template;
	}
	
	private void assignCompute(JSONObject jsonObject, boolean full) throws Exception
	{
		assignItem(jsonObject, full);
		
		if (m_full)
		{
			m_arch = jsonObject.getString("arch");
			m_cores = jsonObject.getInt("cores");
			m_cpu = (float) jsonObject.getDouble("cpu");
			m_memory = jsonObject.getInt("memory");
			m_state = jsonObject.getString("state");
			m_template = jsonObject.getString("template");
		}
	}
	
	private void jsonCompute(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("arch", m_arch);
		jsonObject.put("cores", m_cores);
		jsonObject.put("cpu", m_cpu);
		jsonObject.put("memory", m_memory);
		jsonObject.put("state", m_state);
		jsonObject.put("template", m_template);
	}
	
	private String m_arch;
	private int m_cores;
	private float m_cpu;
	private int m_memory;
	private String m_state;
	private String m_template;
}
