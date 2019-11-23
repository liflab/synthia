package ca.uqac.lif.synthia;

import java.util.Random;

import ca.uqac.lif.cep.synthia.random.Freeze;
import ca.uqac.lif.cep.synthia.random.IpAddressProvider;
import ca.uqac.lif.cep.synthia.random.MarkovChain;
import ca.uqac.lif.cep.synthia.random.RandomString;
import ca.uqac.lif.cep.synthia.random.UniformRandomPicker.RandomFloat;
import ca.uqac.lif.cep.synthia.random.UniformRandomPicker.RandomInteger;
import ca.uqac.lif.cep.synthia.util.Constant;
import ca.uqac.lif.cep.synthia.util.StringPattern;

public class Test 
{
	public static void main(String[] args)
	{
		
		MarkovChain<String> mmm = new MarkovChain<String>(new RandomFloat());
		//mmm.add(0, new StaticObjectProvider<String>("A"));
		mmm.add(0, new StringPattern("{$0} - Source: {$1}, Destination: {$2}", 
				new Freeze<String>(new RandomString(3, 6)), 
				new Freeze<String>(new IpAddressProvider(new RandomInteger(0, 256))), 
				new IpAddressProvider(new RandomInteger(0, 256))));
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
