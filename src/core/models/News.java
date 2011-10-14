package core.models;

import org.json.JSONObject;

public class News extends Item
{
	public News()
	{
		
	}
	
	public String type()
	{
		return m_type;
	}
	
	public String content()
	{
		return m_content;
	}
	
	public boolean assign(Item item)
	{
		boolean success = false;
		
		try
		{
			assignNews((News) item);
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
			News temp = new News();
			temp.assignNews(jsonObject, full);
			assignNews(temp);
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
		String stringRepresentation = "News(";
		stringRepresentation += m_id + ", ";
		stringRepresentation += m_name;
		
		if (m_full)
		{
			stringRepresentation += ", " + m_type + ", ";
			stringRepresentation += m_content;
		}
		
		stringRepresentation += ")";
		
		return stringRepresentation;
	}
	
	private void assignNews(News news)
	{
		assignItem(news);
		m_type = news.m_type;
		m_content = news.m_content;
	}
	
	private void assignNews(JSONObject jsonObject, boolean full) throws Exception
	{
		assignItem(jsonObject, full);
		
		if (m_full)
		{
			m_type = jsonObject.getString("type");
			m_content = jsonObject.getString("content");
		}
	}
	
	private String m_type;
	private String m_content;
}
