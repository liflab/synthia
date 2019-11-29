package sequences;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.sequence.BehaviorTree;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Selector;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Leaf;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Sequence;

/**
 * Generates a random sequence of strings whose content is defined by
 * a behavior tree. In this example, the behavior tree corresponds to this
 * picture:
 * <p>
 * <img src="{@docRoot}/doc-files/Tree.png" alt="Tree">
 * @author sylvain
 *
 */
public class BehaviorTreeExample
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		RandomFloat picker = new RandomFloat();
		picker.setSeed(10);
		BehaviorTree<String> tree = new Sequence<String>(
				new Selector<String>(picker)
					.add(new Leaf<String>("A"), 0.5)
					.add(new Sequence<String>(new Leaf<String>("B"), new Leaf<String>("C")), 0.5),
				new Selector<String>(picker)
					.add(new Leaf<String>("D"), 0.25)
					.add(new Sequence<String>(new Leaf<String>("E"), new Leaf<String>("F")), 0.75)
				);
		String s = null;
		do
		{
			s = tree.pick();
			if (s == null)
			{
				break;
			}
			System.out.println(s);
		} while (s != null);
	}
}
