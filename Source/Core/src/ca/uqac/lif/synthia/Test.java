package ca.uqac.lif.synthia;

import java.util.Random;

import ca.uqac.lif.cep.synthia.random.FreezeProvider;
import ca.uqac.lif.cep.synthia.random.IpAddressProvider;
import ca.uqac.lif.cep.synthia.random.MarkovProvider;
import ca.uqac.lif.cep.synthia.random.RandomStringProvider;
import ca.uqac.lif.cep.synthia.random.StaticObjectProvider;
import ca.uqac.lif.cep.synthia.random.StringPatternProvider;
import ca.uqac.lif.synthia.UniformRandomPicker.UniformFloatSource;
import ca.uqac.lif.synthia.UniformRandomPicker.UniformIntervalIntegerSource;

public class Test 
{
	public static void main(String[] args)
	{
		
		MarkovProvider<String> mmm = new MarkovProvider<String>(new UniformFloatSource());
		//mmm.add(0, new StaticObjectProvider<String>("A"));
		mmm.add(0, new StringPatternProvider("{$0} - Source: {$1}, Destination: {$2}", 
				new FreezeProvider<String>(new RandomStringProvider(3, 6)), 
				new FreezeProvider<String>(new IpAddressProvider(new UniformIntervalIntegerSource(0, 256))), 
				new IpAddressProvider(new UniformIntervalIntegerSource(0, 256))));
		mmm.add(1, new StaticObjectProvider<String>("B"));
		mmm.add(0, 0, 0.9).add(0, 1, 0.1);
		mmm.add(1, 1, 1);
		//RandomSource rand = new FixedRandomSource(0.3, 0.3, 0.6, 0.3);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(mmm.pick());
		}
	}
}
