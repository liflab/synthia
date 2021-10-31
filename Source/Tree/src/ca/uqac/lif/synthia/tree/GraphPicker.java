package ca.uqac.lif.synthia.tree;

import java.util.HashSet;
import java.util.Set;

import ca.uqac.lif.synthia.Picker;

public abstract class GraphPicker<T> implements Picker<Node<T>>
{
	protected Picker<Node<T>> m_nodePicker;
	
	protected Picker<Integer> m_size;
	
	protected Set<Node<T>> m_nodes;
	
	public GraphPicker(Picker<Node<T>> node_picker, Picker<Integer> size)
	{
		super();
		m_nodePicker = node_picker;
		m_size = size;
		m_nodes = new HashSet<Node<T>>();
	}
	
	/**
	 * Connects two nodes in a graph.
	 * @param n1 The first node
	 * @param n2 The second node
	 */
	protected void connect(Node<T> n1, Node<T> n2)
	{
		n1.addChild(n2);
		n2.addChild(n1);
	}
	
	/**
	 * Gets the set of nodes in the last graph generated.
	 * @return The set of nodes
	 */
	public Set<Node<T>> getNodes()
	{
		Set<Node<T>> nodes = new HashSet<Node<T>>(m_nodes.size());
		nodes.addAll(m_nodes);
		return nodes;
	}
	
	/**
	 * Gets the maximum out degree in a set of nodes in the last graph generated.
	 * @return The maximum out degree
	 */
	public int getMaxDegree()
	{
		int max = 0;
		for (Node<?> n : m_nodes)
		{
			max = Math.max(max, n.getChildren().size());
		}
		return max;
	}

	@Override
	public void reset()
	{
		m_nodePicker.reset();
		m_nodes.clear();
	}
}
