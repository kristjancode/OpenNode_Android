package core.config;

public class Config 
{
	public Config()
	{
		m_serverHostname = "0.0.0.0";
		m_serverPort = 0;
		m_serverUsername = "NONE";
		m_serverPassword = "NONE";
	}
	
	public void serverHostname(String serverHostname)
	{
		if (serverHostname != null)
		{
			m_serverHostname = serverHostname;
		}
	}
	
	public void serverPort(int serverPort)
	{
		if (serverPort >= 0)
		{
			m_serverPort = serverPort;
		}
	}
	
	public void serverUsername(String serverUsername)
	{
		if (serverUsername != null)
		{
			m_serverUsername = serverUsername;
		}
	}
	
	public void serverPassword(String serverPassword)
	{
		if (serverPassword != null)
		{
			m_serverPassword = serverPassword;
		}
	}
	
	public String serverHostname()
	{
		return m_serverHostname;
	}
	
	public int serverPort()
	{
		return m_serverPort;
	}
	
	public String serverUsername()
	{
		return m_serverUsername;
	}
	
	public String serverPassword()
	{
		return m_serverPassword;
	}
	
	private String m_serverHostname;
	private int m_serverPort;
	private String m_serverUsername;
	private String m_serverPassword;	
}
