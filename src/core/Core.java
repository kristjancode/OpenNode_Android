package core;

import core.config.Config;
import core.network.ServerConnector;
import core.interfaces.*;

public class Core 
{
	public Core()
	{
		m_serverConnector = new ServerConnector(Config.SERVER_HOSTNAME, Config.SERVER_PORT);
		m_computeManager = new ComputeManager(this);
		m_networkManager = new NetworkManager(this);
		m_storageManager = new StorageManager(this);
		m_templateManager = new TemplateManager(this);
		m_newsManager = new NewsManager(this);
	}
	
	public ServerConnector serverConnector()
	{
		return m_serverConnector;
	}
	
	public ComputeManager computeManager()
	{
		return m_computeManager;
	}
	
	public NetworkManager networkManager()
	{
		return m_networkManager;
	}
	
	public StorageManager storageManager()
	{
		return m_storageManager;
	}
	
	public TemplateManager templateManager()
	{
		return m_templateManager;
	}
	
	public NewsManager newsManager()
	{
		return m_newsManager;
	}
	
	private ServerConnector m_serverConnector;
	private ComputeManager m_computeManager;
	private NetworkManager m_networkManager;
	private StorageManager m_storageManager;
	private TemplateManager m_templateManager;
	private NewsManager m_newsManager;
}
