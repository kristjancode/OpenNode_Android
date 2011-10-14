package core.interfaces;

import core.Core;
import core.models.Template;

public class TemplateManager extends AbstractManager<Template> 
{
	public TemplateManager(Core core)
	{
		super(core, "templates");
	}
	
	@Override
	protected Template itemInstance()
	{
		return new Template();
	}
}
