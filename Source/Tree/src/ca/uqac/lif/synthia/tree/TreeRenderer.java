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

/**
 * Renders a tree of labeled nodes as a <a href="https://graphviz.org">Graphviz</a>
 * input file.
 * @author Sylvain Hallé
 * @ingroup API
 */
public class TreeRenderer 
{
	public static void toDot(PrintStream ps, Node<?> n)
	{
		ps.println("digraph G {");
		ps.println("node [style=\"filled\",shape=\"circle\",label=\"\"];");
		int id_counter = 0;
		ps.println(0 + "[color=\"" + n + "\"];");
		for (Node<?> child : n.getChildren())
		{
			id_counter = toDot(ps, 0, child, id_counter);
		}
		ps.println("}");
	}
	
	protected static int toDot(PrintStream ps, int parent_id, Node<?> current, int id_counter)
	{
		int current_id = ++id_counter;
		ps.println(current_id + "[color=\"" + current + "\"];");
		ps.println(parent_id + " -> " + current_id);
		for (Node<?> child : current.getChildren())
		{
			id_counter = toDot(ps, current_id, child, id_counter);
		}
		return id_counter;
	}
}
