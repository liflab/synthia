package ca.uqac.lif.synthia.map;

import java.util.List;

public class AreaMap
{
	protected int m_width;
	protected int m_height;
	protected List<Point> m_hubs;

	public AreaMap(int width, int height, List<Point> hubs)
	{
		m_width = width;
		m_height = height;
		m_hubs = hubs;
	}

	public int getWidth()
	{
		return m_width;
	}

	public int getHeight()
	{
		return m_height;
	}

	public List<Point> getHubs()
	{
		return m_hubs;
	}
}
