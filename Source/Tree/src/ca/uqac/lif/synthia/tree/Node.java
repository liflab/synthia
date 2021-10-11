package ca.uqac.lif.synthia.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> 
{
	/**
	 * The node's label.
	 */
	protected T m_label;
	
	/**
	 * The children of this node.
	 */
	protected List<Node<T>> m_children;
	
	/**
	 * Creates a new node with given label.
	 * @param label The label
	 */
	public Node(T label)
	{
		super();
		m_label = label;
		m_children = new ArrayList<Node<T>>();
	}
	
	/**
	 * Gets the children of this node.
	 * @return The children
	 */
	public List<Node<T>> getChildren()
	{
		return m_children;
	}
	
	@Override
	public String toString()
	{
		return m_label.toString();
	}
}
