package core.models;

import org.json.JSONObject;

public class Network extends Item
{
	public Network()
	{
		m_ip = null;
		m_mask = null;
		m_addressAllocation = null;
		m_gateway = null;
	}
	
	public String ip()
	{
		return m_ip;
	}
	
	public String mask()
	{
		return m_mask;
	}
	
	public String addressAllocation()
	{
		return m_addressAllocation;
	}
	
	public String gateway()
	{
		return m_gateway;
	}
	
	public boolean assign(Item item)
	{
		boolean success = false;
		
		try
		{
			assignNetwork((Network) item);
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
			Network temp = new Network();
			temp.assignNetwork(jsonObject, full);
			assignNetwork(temp);
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
		String stringRepresentation = "Network(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		
		if (m_full)
		{
			stringRepresentation += ", " + m_ip + ", ";
			stringRepresentation += m_mask + ", ";
			stringRepresentation += m_addressAllocation + ", ";
			stringRepresentation += m_gateway;
		}
		
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	private void assignNetwork(Network network)
	{
		assignItem(network);
		m_ip = network.m_ip;
		m_mask = network.m_mask;
		m_addressAllocation = network.m_addressAllocation;
		m_gateway = network.m_gateway;
	}
	
	private void assignNetwork(JSONObject jsonObject, boolean full) throws Exception
	{
		assignItem(jsonObject, full);
		
		if (m_full)
		{
			m_ip = jsonObject.getString("ip");
			m_mask = jsonObject.getString("mask");
			m_addressAllocation = jsonObject.getString("address_allocation");
			m_gateway = jsonObject.getString("gateway");
		}
	}
	
	private String m_ip;
	private String m_mask;
	private String m_addressAllocation;
	private String m_gateway;
}
