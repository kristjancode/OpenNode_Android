package core.network;

public class ServerConnector 
{
	public ServerConnector(String server, int port)
	{
		m_server = server;
		m_port = port;
		m_networkManager = new Networking();
	}
	
	public String httpGET(String destination)
	{
		return m_networkManager.httpRequest(m_server, m_port, "GET", destination, "");
	}
	
	public String httpPOST(String destination, String data)
	{
		return m_networkManager.httpRequest(m_server, m_port, "POST", destination, data);
	}
	
	public String httpPUT(String destination, String data)
	{
		return m_networkManager.httpRequest(m_server, m_port, "PUT", destination, data);
	}
	
	public String httpDELETE(String destination)
	{
		return m_networkManager.httpRequest(m_server, m_port, "DELETE", destination, "");
	}
	
	private String m_server;
	private int m_port;
	private Networking m_networkManager;
}
