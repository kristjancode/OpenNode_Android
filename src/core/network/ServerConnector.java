package core.network;


public class ServerConnector 
{
	public ServerConnector(String server, int port)
	{
		m_server = server;
		m_port = port;
		m_networkManager = new Networking();
	}
	
	public String request(String destination)
	{
		return m_networkManager.httpRequest(m_server, m_port, destination);
	}
	
	private String m_server;
	private int m_port;
	private Networking m_networkManager;
}
