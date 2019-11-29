package ca.uqac.lif.synthia;

import basic.IpAddressProvider;
import ca.uqac.lif.synthia.random.BoundedPicker;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.random.RandomPicker.RandomFloat;
import ca.uqac.lif.synthia.random.RandomPicker.RandomInteger;
import ca.uqac.lif.synthia.replay.Playback;
import ca.uqac.lif.synthia.sequence.Interleave;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.StringPattern;
import ca.uqac.lif.synthia.util.Tick;;

public class Inter {

	public static void main(String[] args) 
	{
		//FloatSource tick_source = new UniformIntervalFloatSource(0.5, 1);
		Picker<Number> tick_source = new Playback<Number>(1, 2, 3);
		Tick ticker = new Tick(1000, tick_source);
		StringPattern rsp1 = new StringPattern("{$0} - {$1}", 
				new Freeze<String>(new RandomString(3, 5)), 
				new IpAddressProvider(new RandomInteger(0, 256)));
		BoundedPicker<String> bp1 = new BoundedPicker<String>(new TickLineProvider(ticker, rsp1), new RandomInteger(2, 10));
		StringPattern rsp2 = new StringPattern("FOO [{$0};{$1}]", 
				new Freeze<String>(new RandomString(3, 5)), 
				new RandomString(5, 8));
		BoundedPicker<String> bp2 = new BoundedPicker<String>(new TickLineProvider(ticker, rsp2), new RandomInteger(5, 6));
		Interleave<String> ip = new Interleave<String>(new RandomFloat(), 0.5, 0.85);
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
		
		protected Tick m_tickProvider;
		
		public TickLineProvider(Tick tick_provider, Picker<String> provider)
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
