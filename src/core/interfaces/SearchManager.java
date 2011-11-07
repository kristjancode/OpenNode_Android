package core.interfaces;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import core.Core;
import core.network.ServerConnector;
import core.models.*;

public class SearchManager 
{
	public SearchManager(Core core)
	{
		m_core = core;
		m_serverConnector = m_core.serverConnector();
		m_searchResults = new ArrayList<Item>();
		resetFilters();
	}
	
	public void resetFilters()
	{
		m_filterComputes = false;
		m_filterNetworks = false;
		m_filterStorages = false;
		m_filterTemplates = false;
		m_filterNews = false;
	}
	
	public void filterComputes(boolean filterComputes)
	{
		m_filterComputes = filterComputes;
	}
	
	public void filterNetworks(boolean filterNetworks)
	{
		m_filterNetworks = filterNetworks;
	}
	
	public void filterStorages(boolean filterStorages)
	{
		m_filterStorages = filterStorages;
	}
	
	public void filterTemplates(boolean filterTemplates)
	{
		m_filterTemplates = filterTemplates;
	}
	
	public void filterNews(boolean filterNews)
	{
		m_filterNews = filterNews;
	}
	
	public boolean filterComputes()
	{
		return m_filterComputes;
	}
	
	public boolean filterNetworks()
	{
		return m_filterNetworks;
	}
	
	public boolean filterStorages()
	{
		return m_filterStorages;
	}
	
	public boolean filterTemplates()
	{
		 return m_filterTemplates;
	}
	
	public boolean filterNews()
	{
		return m_filterNews;
	}
	
	public int count()
	{
		return m_searchResults.size();
	}
	
	public Item item(int index)
	{
		Item item = null;
		
		if (index >= 0 && index < m_searchResults.size())
		{
			item = m_searchResults.get(index);
		}
		
		return item;
	}
	
	public boolean search(String[] tags)
	{
		boolean success = false;
		
		try
		{
			m_searchResults.clear();
			ArrayList<String> searchDestinations = searchDestinations();
			String searchQuery = searchQuery(tags);
			int size = searchDestinations.size();
			
			for (int i = 0; i < size; i++)
			{
				String response = m_serverConnector.httpGET(searchDestinations.get(i) + searchQuery);
				JSONArray jsonArray = new JSONArray(response);
				int length = jsonArray.length();
				
				for (int j = 0; j < length; j++)
				{
					JSONObject jsonObject = jsonArray.getJSONObject(j);
					Item item = null;			
					item = item != null ? item : (item = new Compute()).assign(jsonObject) ? item : null;
					item = item != null ? item : (item = new Network()).assign(jsonObject) ? item : null;
					item = item != null ? item : (item = new Storage()).assign(jsonObject) ? item : null;
					item = item != null ? item : (item = new Template()).assign(jsonObject) ? item : null;
					item = item != null ? item : (item = new News()).assign(jsonObject) ? item : null;
					
					if (item != null)
					{
						m_searchResults.add(item);
					}
				}
			}
			
			success = m_core.computeManager().loadSearchResults();
			success = success ? m_core.networkManager().loadSearchResults() : false;
			success = success ? m_core.storageManager().loadSearchResults() : false;
			success = success ? m_core.templateManager().loadSearchResults() : false;
			success = success ? m_core.newsManager().loadSearchResults() : false;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private ArrayList<String> searchDestinations()
	{
		ArrayList<String> searchDestinations = new ArrayList<String>();
		
		if (m_filterComputes)
		{
			searchDestinations.add("computes/");
		}
		
		if (m_filterNetworks)
		{
			searchDestinations.add("networks/");
		}
		
		if (m_filterStorages)
		{
			searchDestinations.add("storages/");
		}
		
		if (m_filterTemplates)
		{
			searchDestinations.add("templates/");
		}
		
		if (m_filterNews)
		{
			searchDestinations.add("news/");
		}
		
		if (searchDestinations.size() == 0)
		{
			searchDestinations.add("");
		}
		
		return searchDestinations;
	}
	
	private String searchQuery(String[] tags)
	{
		int length = tags.length;
		String searchQuery = length > 0 ? "?q=" : "";
		
		for (int i = 0; i < length; i++)
		{
			searchQuery += tags[i];
			searchQuery += i < (length - 1) ? "&" : "";
		}
		
		return searchQuery;
	}
	
	private Core m_core;
	private ServerConnector m_serverConnector;
	private boolean m_filterComputes;
	private boolean m_filterNetworks;
	private boolean m_filterStorages;
	private boolean m_filterTemplates;
	private boolean m_filterNews;
	private ArrayList<Item> m_searchResults;
}
