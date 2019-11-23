package basic;

import ca.uqac.lif.cep.synthia.random.MarkovChain;
import ca.uqac.lif.cep.synthia.random.RandomFloat;
import ca.uqac.lif.cep.synthia.util.Constant;
import ca.uqac.lif.cep.synthia.util.Enumerate;
import ca.uqac.lif.cep.synthia.util.StringPattern;
import ca.uqac.lif.cep.synthia.util.Tick;

public class MarkovSimple {

	public static void main(String[] args)
	{
		Tick tick = new Tick(0, new Enumerate<Integer>(1, 2, 1));
		MarkovChain<String> mc = new MarkovChain<String>(new RandomFloat());
		mc.add(0, new Constant<String>(""));
		mc.add(1, new Constant<String>("START"));
		mc.add(2, new StringPattern("{$0},A", tick));
		mc.add(3, new StringPattern("{$0},B", tick));
		mc.add(4, new StringPattern("{$0},C", tick));
		mc.add(0, 1, 1).add(1, 2, 0.9).add(1, 4, 0.1);
		mc.add(2, 3, 1).add(3, 2, 0.7).add(3, 4, 0.3).add(4, 4, 1);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(mc.pick());
		}
	}

}
