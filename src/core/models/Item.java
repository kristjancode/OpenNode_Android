package core.models;

public abstract class Item 
{
	protected Item()
	{
		m_id = 0;
		m_name = null;
		m_full = false;
	}
	
	protected void assign(Item item)
	{
		m_id = item.m_id;
		m_name = item.m_name;
		m_full = item.m_full;
	}
	
	public int id()
	{
		return m_id;
	}
	
	public String name()
	{
		return m_name;
	}
	
	public boolean full()
	{
		return m_full;
	}
	
	@Override
	public abstract String toString();
	
	protected int m_id;
	protected String m_name;
	protected boolean m_full;
}
