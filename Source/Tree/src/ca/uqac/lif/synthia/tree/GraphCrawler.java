package ca.uqac.lif.synthia.tree;

import java.util.HashMap;
import java.util.Map;

public abstract class GraphCrawler<T>
{
	protected Map<Node<T>,Integer> m_nodes;
	
	protected int m_idCounter;
	
	public GraphCrawler()
	{
		super();
		m_nodes = new HashMap<Node<T>,Integer>();
		m_idCounter = 0;

	}
	
	protected int getId(Node<T> n)
	{
		if (m_nodes.containsKey(n))
		{
			return m_nodes.get(n);
		}
		int id = m_idCounter++;
		m_nodes.put(n, id);
		return id;
	}

}
