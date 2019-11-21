package ca.uqac.lif.synthia;

import ca.uqac.lif.cep.synthia.random.BoundedProvider;
import ca.uqac.lif.cep.synthia.random.FreezeProvider;
import ca.uqac.lif.cep.synthia.random.InterleavedProvider;
import ca.uqac.lif.cep.synthia.random.IpAddressProvider;
import ca.uqac.lif.cep.synthia.random.RandomStringProvider;
import ca.uqac.lif.cep.synthia.random.StringPatternProvider;
import ca.uqac.lif.cep.synthia.random.TickProvider;
import ca.uqac.lif.synthia.UniformRandomPicker.UniformFloatSource;
import ca.uqac.lif.synthia.UniformRandomPicker.UniformIntervalIntegerSource;;

public class Inter {

	public static void main(String[] args) 
	{
		//FloatSource tick_source = new UniformIntervalFloatSource(0.5, 1);
		Picker<Number> tick_source = new ListPicker<Number>(1, 2, 3);
		TickProvider ticker = new TickProvider(1000, tick_source);
		StringPatternProvider rsp1 = new StringPatternProvider("{$0} - {$1}", 
				new FreezeProvider<String>(new RandomStringProvider(3, 5)), 
				new IpAddressProvider(new UniformIntervalIntegerSource(0, 256)));
		BoundedProvider<String> bp1 = new BoundedProvider<String>(new TickLineProvider(ticker, rsp1), new UniformIntervalIntegerSource(2, 10));
		StringPatternProvider rsp2 = new StringPatternProvider("FOO [{$0};{$1}]", 
				new FreezeProvider<String>(new RandomStringProvider(3, 5)), 
				new RandomStringProvider(5, 8));
		BoundedProvider<String> bp2 = new BoundedProvider<String>(new TickLineProvider(ticker, rsp2), new UniformIntervalIntegerSource(5, 6));
		InterleavedProvider<String> ip = new InterleavedProvider<String>(new UniformFloatSource(), 0.5, 0.85);
		ip.add(bp1, 0.55);
		ip.add(bp2, 0.45);
		for (int i = 0; i < 25; i++)
		{
			System.out.println(ip.pick());
		}
	}
	
	public static class TickLineProvider implements Picker<String>
	{
		protected Picker<String> m_provider;
		
		protected TickProvider m_tickProvider;
		
		public TickLineProvider(TickProvider tick_provider, Picker<String> provider)
		{
			super();
			m_tickProvider = tick_provider;
			m_provider = provider;
		}
		
		@Override
		public void reset() 
		{
			m_tickProvider.reset();
			m_provider.reset();
		}

		@Override
		public String pick() 
		{
			Number tick = m_tickProvider.pick();
			String line = m_provider.pick();
			if (tick == null || line == null)
			{
				return null;
			}
			return tick + "," + line;
		}

		@Override
		public TickLineProvider duplicate(boolean with_state) 
		{
			// ticker is not duplicated, so that all instances of the provider
			// share the same clock
			return new TickLineProvider(m_tickProvider, m_provider.duplicate(with_state));
		}
		
	}

}
