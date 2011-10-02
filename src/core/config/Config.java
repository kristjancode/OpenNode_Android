package core.config;

public class Config 
{
	private Config()
	{
		
	}
	
	static
	{
		SERVER_HOSTNAME = "127.0.0.1";
		SERVER_PORT = 8080;
	}
	
	public static String SERVER_HOSTNAME;
	public static int SERVER_PORT;
}
