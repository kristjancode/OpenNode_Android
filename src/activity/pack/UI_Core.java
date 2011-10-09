package activity.pack;

import core.Core;
import core.config.Config;

public class UI_Core {

	private static Core core;
	
	public static Core getCore(){
		return core;
	}
	static{
		Config.SERVER_HOSTNAME = "10.0.2.2";
		Config.SERVER_PORT = 8080;
		core = new Core();
	}
	
}
