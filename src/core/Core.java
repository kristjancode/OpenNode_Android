package core;

import core.config.Config;
import core.network.ServerConnector;
import core.interfaces.*;

public class Core 
{
	public Core()
	{
		m_config = new Config();
		m_serverConnector = new ServerConnector(m_config);
		m_computeManager = new ComputeManager(this);
		m_networkManager = new NetworkManager(this);
		m_storageManager = new StorageManager(this);
		m_templateManager = new TemplateManager(this);
		m_newsManager = new NewsManager(this);
	}
	
	public Config config()
	{
		return m_config;
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
	
	private Config m_config;
	private ServerConnector m_serverConnector;
	private ComputeManager m_computeManager;
	private NetworkManager m_networkManager;
	private StorageManager m_storageManager;
	private TemplateManager m_templateManager;
	private NewsManager m_newsManager;
}
