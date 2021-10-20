package mutation;

import java.util.List;

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.Mutate;
import ca.uqac.lif.synthia.util.Swap;
import ca.uqac.lif.synthia.util.Tick;

public class MutatedLists
{

	public static void main(String[] args)
	{
		RandomFloat rf = new RandomFloat();
		RandomInteger start = new RandomInteger(0, 100);
		Tick t = new Tick(start, new Constant<Integer>(1));
		ComposeList<Number> lists = new ComposeList<Number>(t, new RandomInteger(2, 10));
		Swap<Number> swap = new Swap<Number>(lists, rf, rf);
		Mutate<List<Number>> m = new Mutate<List<Number>>(new Freeze<List<Number>>(lists), new Constant<Mutator<List<Number>>>(swap));
		for (int i = 0; i < 10; i++)
		{
			System.out.println(m.pick());	
		}
		
	}

}
