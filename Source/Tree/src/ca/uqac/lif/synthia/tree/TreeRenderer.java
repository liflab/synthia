package ca.uqac.lif.synthia.tree;

import java.io.PrintStream;

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
