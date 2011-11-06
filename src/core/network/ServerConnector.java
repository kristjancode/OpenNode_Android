package core.network;

import core.config.Config;

public class ServerConnector 
{
	public ServerConnector(Config config)
	{
		m_config = config;
		m_networkManager = new Networking();
	}
	
	public boolean authenticate()
	{
		return httpGET("computes/") != null;
	}
	
	public String httpGET(String destination)
	{
		return m_networkManager.httpRequest(m_config.serverHostname(), m_config.serverPort(), m_config.serverUsername(), m_config.serverPassword(), "GET", destination, "");
	}
	
	public String httpPOST(String destination, String data)
	{
		return m_networkManager.httpRequest(m_config.serverHostname(), m_config.serverPort(), m_config.serverUsername(), m_config.serverPassword(), "POST", destination, data);
	}
	
	public String httpPUT(String destination, String data)
	{
		return m_networkManager.httpRequest(m_config.serverHostname(), m_config.serverPort(), m_config.serverUsername(), m_config.serverPassword(), "PUT", destination, data);
	}
	
	public String httpDELETE(String destination)
	{
		return m_networkManager.httpRequest(m_config.serverHostname(), m_config.serverPort(), m_config.serverUsername(), m_config.serverPassword(), "DELETE", destination, "");
	}
	
	private Config m_config;
	private Networking m_networkManager;
}
