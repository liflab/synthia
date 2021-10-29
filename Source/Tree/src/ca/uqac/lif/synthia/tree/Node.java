/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.synthia.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of a labeled nodel
 * @author Sylvain Hallé
 *
 * @param <T> The type of the node's label
 * @ingroup API
 */
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
