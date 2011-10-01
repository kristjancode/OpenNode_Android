package core.models;

import org.json.JSONObject;

public class Network extends Item
{
	private Network()
	{
		m_ip = null;
		m_mask = null;
		m_addressAllocation = null;
		m_gateway = null;
	}
	
	public void assign(Network network)
	{
		super.assign(network);
		m_ip = network.m_ip;
		m_mask = network.m_mask;
		m_addressAllocation = network.m_addressAllocation;
		m_gateway = network.m_gateway;
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
	
	public static Network parse(JSONObject jsonObject, boolean full)
	{
		Network network = null;
		Network temp = new Network();
		
		if (parse(temp, jsonObject, full))
		{
			network = temp;
		}
		
		return network;
	}
	
	public static boolean parse(Network network, JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			Network temp = new Network();
			temp.m_full = full;
			
			if (full)
			{
				temp.m_id = jsonObject.getInt("id");
				temp.m_name = jsonObject.getString("name");
				temp.m_ip = jsonObject.getString("ip");
				temp.m_mask = jsonObject.getString("mask");
				temp.m_addressAllocation = jsonObject.getString("address_allocation");
				temp.m_gateway = jsonObject.getString("gateway");
			}
			else
			{
				String key = (String) jsonObject.keys().next();
				temp.m_id = Integer.parseInt(key);
				temp.m_name = jsonObject.getString(key);
			}
			
			network.assign(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private String m_ip;
	private String m_mask;
	private String m_addressAllocation;
	private String m_gateway;
}
