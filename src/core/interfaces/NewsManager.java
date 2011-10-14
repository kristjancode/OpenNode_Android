package core.interfaces;

import core.Core;
import core.models.News;

public class NewsManager extends AbstractManager<News>
{
	public NewsManager(Core core)
	{
		super(core, "news");
	}
	
	@Override
	protected News itemInstance()
	{
		return new News();
	}
}
