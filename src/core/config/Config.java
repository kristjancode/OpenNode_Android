package core.config;

public class Config 
{
	public Config()
	{
		m_serverHostname = null;
		m_serverPort = 0;
		m_serverUsername = null;
		m_serverPassword = null;
	}
	
	public void serverHostname(String serverHostname)
	{
		m_serverHostname = serverHostname;
	}
	
	public void serverPort(int serverPort)
	{
		m_serverPort = serverPort;
	}
	
	public void serverUsername(String serverUsername)
	{
		m_serverUsername = serverUsername;
	}
	
	public void serverPassword(String serverPassword)
	{
		m_serverPassword = serverPassword;
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
