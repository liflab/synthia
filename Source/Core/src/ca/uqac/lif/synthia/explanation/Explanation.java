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
package ca.uqac.lif.synthia.explanation;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ca.uqac.lif.dag.LabelledNode;
import ca.uqac.lif.dag.NestedNode.NestedNodeCrawler;
import ca.uqac.lif.dag.Node;
import ca.uqac.lif.dag.Pin;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.petitpoucet.function.RelationNodeFactory;
import ca.uqac.lif.synthia.Picker;

/**
 * Constructs an explanation graph for the output produced by a picker.
 * @author Sylvain Hallé
 * @ingroup API
 */
public class Explanation
{
	public static PartNode explain(Part part, Picker<?> picker)
	{
		RelationNodeFactory factory = RelationNodeFactory.getFactory().getFactory(part, picker);
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
