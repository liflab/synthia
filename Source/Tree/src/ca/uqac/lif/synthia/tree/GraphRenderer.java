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

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Renders a tree of labeled nodes as a <a href="https://graphviz.org">Graphviz</a>
 * input file.
 * @author Sylvain Hallé
 * @ingroup API
 */
public class GraphRenderer<T> extends GraphCrawler<T>
{
	
	protected boolean m_directed;
	
	protected String m_nodeString;
	
	public GraphRenderer(boolean directed)
	{
		super();
		m_directed = directed;
		m_nodeString = "[style=\"filled\",shape=\"circle\",label=\"\"]";
	}
	
	public GraphRenderer<T> setNodeString(String s)
	{
		m_nodeString = s;
		return this;
	}
	
	public void printToDot(PrintStream ps, Node<T> n)
	{
		if (m_directed)
		{
			ps.println("digraph G {");
		}
		else
		{
			ps.println("graph G {");
		}
		ps.println("node " + m_nodeString + ";");
		Set<Node<T>> visited = new HashSet<Node<T>>();
		Queue<Node<T>> to_visit = new ArrayDeque<Node<T>>();
		to_visit.add(n);
		while (!to_visit.isEmpty())
		{
			Node<T> current = to_visit.remove();
			if (visited.contains(current))
			{
				continue;
			}
			visited.add(current);
			int id = getId(current);
			ps.println(id + " [color=\"" + getColor(current) + "\",label=\"" + getLabel(current) + "\"];");
			for (Node<T> child : current.getChildren())
			{
				int t_id = getId(child);
				if (m_directed)
				{
					ps.println(id + " -> " + t_id + ";");
				}
				else
				{
					if (!visited.contains(child))
					{
						ps.println(id + " -- " + t_id + ";");	
					}
				}
				if (!visited.contains(child) && !to_visit.contains(child))
				{
					to_visit.add(child);
				}
			}
		}
		ps.println("}");
	}
	
	protected String getColor(Node<T> n)
	{
		return "grey";
	}
	
	protected String getLabel(Node<T> n)
	{
		return n.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static void toDot(PrintStream ps, Node<? extends Object> start)
	{
		GraphRenderer<Object> gr = new GraphRenderer<Object>(false);
		gr.printToDot(ps, (Node<Object>) start);
	}
}