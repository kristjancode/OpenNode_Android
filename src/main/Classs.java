package main;

import core.Core;
import core.config.Config;

public class Classs 
{
	public static Core getCore()
	{
		return core;
	}
	
	static
	{
		Config.SERVER_HOSTNAME = "10.0.2.2";
		Config.SERVER_PORT = 8080;
		core = new Core();
	}
	
	private static Core core;
}
