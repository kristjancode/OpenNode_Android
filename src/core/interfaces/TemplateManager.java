package core.interfaces;

import org.json.JSONObject;
import core.Core;
import core.models.Template;

public class TemplateManager extends AbstractManager<Template> 
{
	public TemplateManager(Core core)
	{
		super(core, "templates");
	}
	
	@Override
	protected Template parseItem(JSONObject jsonObject, boolean full)
	{
		return Template.parse(jsonObject, full);
	}
	
	@Override
	protected boolean parseItem(Template template, JSONObject jsonObject, boolean full)
	{
		return Template.parse(template, jsonObject, full);
	}
}
