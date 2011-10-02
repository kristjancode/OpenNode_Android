package core.models;

import org.json.JSONObject;

public class News extends Item
{
	private News()
	{
		
	}
	
	private void assign(News news)
	{
		super.assign(news);
		m_type = news.m_type;
		m_content = news.m_content;
	}
	
	public String title()
	{
		return m_name;
	}
	
	public String type()
	{
		return m_type;
	}
	
	public String content()
	{
		return m_content;
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
	
	public static News parse(JSONObject jsonObject, boolean full)
	{
		News news = null;
		News temp = new News();
		
		if (parse(temp, jsonObject, full))
		{
			news = temp;
		}
		
		return news;
	}
	
	public static boolean parse(News news, JSONObject jsonObject, boolean full)
	{
		boolean success = false;
		
		try
		{
			News temp = new News();
			temp.m_full = full;
			
			if (full)
			{
				temp.m_id = jsonObject.getInt("id");
				temp.m_name = jsonObject.getString("title");
				temp.m_type = jsonObject.getString("type");
				temp.m_content = jsonObject.getString("content");
			}
			else
			{
				String key = (String) jsonObject.keys().next();
				temp.m_id = Integer.parseInt(key);
				temp.m_name = jsonObject.getString(key);
			}
			
			news.assign(temp);
			success = true;
		}
		catch (Exception exception)
		{
			
		}
		
		return success;
	}
	
	private String m_type;
	private String m_content;
}
