package ca.uqac.lif.synthia;

import java.util.Random;

import basic.IpAddressProvider;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.sequence.MarkovChain;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.StringPattern;
import ca.uqac.lif.synthia.util.Tick;

public class Test 
{
	public static void main(String[] args)
	{
		
		MarkovChain<String> mmm = new MarkovChain<String>(new RandomFloat());
		//mmm.add(0, new StaticObjectProvider<String>("A"));
		mmm.add(0, new StringPattern("{$0} {$1} - Source: {$2}, Destination: {$3}, Duration: {$4}",
				new Tick(),
				new Freeze<String>(new RandomString(new RandomInteger(3, 6))), 
				new Freeze<String>(new IpAddressProvider(new RandomInteger(0, 256))), 
				new IpAddressProvider(new RandomInteger(0, 256)),
				new RandomInteger(10, 1000)));
		mmm.add(1, new Constant<String>("B"));
		mmm.add(0, 0, 0.9).add(0, 1, 0.1);
		mmm.add(1, 1, 1);
		//RandomSource rand = new FixedRandomSource(0.3, 0.3, 0.6, 0.3);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(mmm.pick());
		}
	}
}
