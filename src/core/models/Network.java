package core.models;

import org.json.JSONObject;

public class Network extends Item
{
	public Network()
	{
		super();
		m_ip = null;
		m_mask = null;
		m_addressAllocation = null;
		m_gateway = null;
	}
	
	public Network(int id, String name, String ip, String mask, String addressAllocation, String gateway)
	{
		super(id, name);
		m_ip = ip;
		m_mask = mask;
		m_addressAllocation = addressAllocation;
		m_gateway = gateway;
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
	
	public boolean assign(JSONObject jsonObject)
	{
		boolean success = false;
		
		try
		{
			Network temp = new Network();
			temp.assignNetwork(jsonObject);
			assignNetwork(temp);
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
			valid = valid ? m_ip != null : false;
			valid = valid ? m_mask != null : false;
			valid = valid ? m_addressAllocation != null : false;
			valid = valid ? m_gateway != null : false;
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
			jsonNetwork(jsonObject);
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
		String stringRepresentation = "Network(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		stringRepresentation += ", " + m_ip + ", ";
		stringRepresentation += m_mask + ", ";
		stringRepresentation += m_addressAllocation + ", ";
		stringRepresentation += m_gateway;		
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
	
	private void assignNetwork(JSONObject jsonObject) throws Exception
	{
		assignItem(jsonObject);
		m_ip = jsonObject.getString("ip");
		m_mask = jsonObject.getString("mask");
		m_addressAllocation = jsonObject.getString("address_allocation");
		m_gateway = jsonObject.getString("gateway");
	}
	
	private void jsonNetwork(JSONObject jsonObject) throws Exception
	{
		jsonItem(jsonObject);
		jsonObject.put("ip", m_ip);
		jsonObject.put("mask", m_mask);
		jsonObject.put("address_allocation", m_addressAllocation);
		jsonObject.put("gateway", m_gateway);
	}
	
	private String m_ip;
	private String m_mask;
	private String m_addressAllocation;
	private String m_gateway;
}
