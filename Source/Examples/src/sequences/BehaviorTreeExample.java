package sequences;

import ca.uqac.lif.cep.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.sequence.BehaviorTree;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Choice;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Leaf;
import ca.uqac.lif.synthia.sequence.BehaviorTree.Sequence;

public class BehaviorTreeExample
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		Picker<Float> picker = new RandomFloat();
		BehaviorTree<String> tree = new Sequence<String>(
				new Choice<String>(picker)
					.add(new Leaf<String>("A"), 0.5)
					.add(new Sequence<String>(new Leaf<String>("B"), new Leaf<String>("C")), 0.5),
				new Choice<String>(picker)
					.add(new Leaf<String>("D"), 0.75)
					.add(new Leaf<String>("E"), 0.25)
				);
		String s = null;
		do
		{
			s = tree.pick();
			System.out.println(s);
		} while (s != null);
	}
}
