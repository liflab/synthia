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

import ca.uqac.lif.synthia.Picker;

/**
 * Generates a tree of labeled nodes.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the node's labels
 * @ingroup API
 */
public class TreePicker<T> implements Picker<Node<T>>
{
	protected Picker<Integer> m_totalHeight;
	
	protected Picker<Float> m_child;
	
	protected Picker<Node<T>> m_node;
	
	protected Picker<Integer> m_degree;
	
	public TreePicker(Picker<Node<T>> node, Picker<Integer> total_height, Picker<Integer> degree, Picker<Float> child)
	{
		super();
		m_totalHeight = total_height;
		m_child = child;
		m_node = node;
		m_degree = degree;
	}
	
	@Override
	public void reset() 
	{
		m_totalHeight.reset();
		m_node.reset();
		m_degree.reset();
	}

	@Override
	public Node<T> pick() 
	{
		Node<T> root = m_node.pick();
		int target_height = m_totalHeight.pick();
		System.out.println("Height " + target_height);
		List<Node<T>> list = new ArrayList<Node<T>>(1);
		list.add(root);
		populate(list, 0, target_height - 1, 1);
		return root;
	}
	
	protected void populate(List<Node<T>> list, int child_index, int target_height, int current_height)
	{
		if (list.isEmpty() || target_height == 0)
		{
			return;
		}
		for (int i = 0; i < list.size(); i++)
		{
			Node<T> node = list.get(i);
			if (i == child_index)
			{
				int degree = 0;
				do
				{
					degree = m_degree.pick();
				} while (degree == 0);
				int new_child_index = (int) Math.floor(m_child.pick() * (float) degree);
				for (int j = 0; j < degree; j++)
				{
					node.m_children.add(m_node.pick());
				}
				populate(node.m_children, new_child_index, target_height - 1, current_height + 1);
			}
			else
			{
				int degree = m_degree.pick();
				int new_child_index = (int) Math.floor(m_child.pick() * (float) degree);
				int new_height = Math.min(target_height, Math.max(0, m_totalHeight.pick() - current_height));
				for (int j = 0; j < degree; j++)
				{
					node.m_children.add(m_node.pick());
				}
				populate(node.m_children, new_child_index, new_height, current_height + 1);
			}
		}
		
	}

	@Override
	public Picker<Node<T>> duplicate(boolean with_state) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
