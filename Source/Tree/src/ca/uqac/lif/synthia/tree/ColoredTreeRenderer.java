package ca.uqac.lif.synthia.tree;

import java.io.PrintStream;

public class ColoredTreeRenderer extends GraphRenderer<String>
{
	protected ColoredTreeRenderer()
	{
		super(true);
	}
	
	public static void treeToDot(PrintStream ps, Node<String> root)
	{
		ColoredTreeRenderer ctr = new ColoredTreeRenderer();
		ctr.printToDot(ps, root);
	}
	
	@Override
	protected String getLabel(Node<String> n)
	{
		return "";
	}
	
	@Override
	protected String getColor(Node<String> n)
	{
		return n.toString();
	}
}
