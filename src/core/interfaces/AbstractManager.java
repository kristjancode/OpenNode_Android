package core.interfaces;

import java.util.List;
import java.util.LinkedList;
import org.json.JSONObject;
import org.json.JSONArray;
import core.Core;
import core.models.Item;
import core.network.ServerConnector;

public abstract class AbstractManager<T extends Item> 
{
	public AbstractManager(Core core, String request)
	{
		m_core = core;
		m_serverConnector = core.serverConnector();
		m_items = new LinkedList<T>();
		m_request = request;
	}
	
	public int itemCount()
	{
		return m_items.size();
	}
	
	public T item(int index)
	{
		T item = null;
		
		if (index < m_items.size())
		{
			item = m_items.get(index);
		}
		
		return item;
	}
	
	public boolean loadItems()
	{
		return requestItems();
	}
	
	public boolean loadItemDetails(T item)
	{
		return requestItemDetails(item);
	}
	
	private boolean requestItems()
	{
		boolean success = false;
		
		try
		{
			m_items.removeAll(m_items);
			String response = m_serverConnector.request(m_request);
			JSONArray jsonArray = new JSONArray(response);
			int length = jsonArray.length();
			
			for (int i = 0; i < length; i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				T item = parseItem(jsonObject, false);
				
				if (item != null)
				{
					m_items.add(item);
				}
			}
			
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private boolean requestItemDetails(T item)
	{
		boolean success = false;
		
		try
		{
			String response = m_serverConnector.request(m_request + "/" + item.id());
			JSONObject jsonObject = new JSONObject(response);
			success = parseItem(item, jsonObject, true);
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	protected abstract T parseItem(JSONObject jsonObject, boolean full);
	protected abstract boolean parseItem(T item, JSONObject jsonObject, boolean full);
	
	protected Core m_core;
	protected ServerConnector m_serverConnector;
	protected List<T> m_items;
	protected String m_request;
}
