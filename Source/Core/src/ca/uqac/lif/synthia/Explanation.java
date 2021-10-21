package ca.uqac.lif.synthia;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ca.uqac.lif.dag.LabelledNode;
import ca.uqac.lif.dag.NestedNode.NestedNodeCrawler;
import ca.uqac.lif.dag.Node;
import ca.uqac.lif.dag.Pin;
import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;

public class Explanation
{
	public static PartNode explain(Part part, Picker<?> picker)
	{
		NodeFactory factory = NodeFactory.getFactory().getFactory(part, picker);
		Queue<PartNode> to_visit = new ArrayDeque<PartNode>();
		PartNode root = factory.getPartNode(part, picker);
		to_visit.add(root);
		Set<PartNode> visited = new HashSet<PartNode>();
		while (!to_visit.isEmpty())
		{
			LabelledNode current = to_visit.remove();
			if (visited.contains(current) || !(current instanceof PartNode))
			{
				continue;
			}
			PartNode pn_current = (PartNode) current;
			visited.add(pn_current);
			if (!(pn_current.getSubject() instanceof ExplanationQueryable))
			{
				continue;
			}
			Part ppn_current = pn_current.getPart();
			ExplanationQueryable eq = (ExplanationQueryable) pn_current.getSubject();
			PartNode under = eq.getExplanation(ppn_current, factory);
			if (under.equals(pn_current))
			{
				for (Pin<?> p : under.getOutputLinks(0))
				{
					Node n_child = (Node) p.getNode();
					pn_current.addChild(n_child);
					NestedNodeCrawler c = new NestedNodeCrawler(n_child);
					c.crawl();
					List<Node> leaves = c.getLeaves();
					for (Node n : leaves)
					{
						if (n instanceof PartNode && !to_visit.contains(n) && !visited.contains(n))
						{
							to_visit.add((PartNode) n);
						}
					}
				}
			}
			else
			{
				pn_current.addChild(under);
				NestedNodeCrawler c = new NestedNodeCrawler(under);
				List<Node> leaves = c.getLeaves();
				for (Node n : leaves)
				{
					if (n instanceof PartNode && !to_visit.contains(n) && !visited.contains(n))
					{
						to_visit.add((PartNode) n);
					}
				}
			}
		}
		return root;
	}
}
