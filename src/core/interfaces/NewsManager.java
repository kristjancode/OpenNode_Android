package core.interfaces;

import org.json.JSONObject;
import core.Core;
import core.models.News;

public class NewsManager extends AbstractManager<News>
{
	public NewsManager(Core core)
	{
		super(core, "news");
	}
	
	@Override
	protected News parseItem(JSONObject jsonObject, boolean full)
	{
		return News.parse(jsonObject, full);
	}
	
	@Override
	protected boolean parseItem(News news, JSONObject jsonObject, boolean full)
	{
		return News.parse(news, jsonObject, full);
	}
}
