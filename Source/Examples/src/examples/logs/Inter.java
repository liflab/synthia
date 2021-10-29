package examples.logs;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.enumerative.Bound;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.sequence.Knit;
import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.string.StringPattern;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.Tick;
import examples.basic.IpAddressProvider;;

/**
 * @ingroup Examples
 */
public class Inter 
{

	public static void main(String[] args) 
	{
		//FloatSource tick_source = new UniformIntervalFloatSource(0.5, 1);
		Picker<Number> tick_source = new Playback<Number>(new Number[] {1, 2, 3});
		RandomInteger length_1 = new RandomInteger(3, 5);
		RandomInteger length_2 = new RandomInteger(5, 8);
		Tick ticker = new Tick(1000, tick_source);
		StringPattern rsp1 = new StringPattern("{$0} - {$1}", 
				new Freeze<String>(new RandomString(length_1)), 
				new IpAddressProvider(new RandomInteger(0, 256)));
		Bound<String> bp1 = new Bound<String>(new TickLineProvider(ticker, rsp1), new RandomInteger(2, 10));
		StringPattern rsp2 = new StringPattern("FOO [{$0};{$1}]", 
				new Freeze<String>(new RandomString(length_1)), 
				new RandomString(length_2));
		Bound<String> bp2 = new Bound<String>(new TickLineProvider(ticker, rsp2), new RandomInteger(5, 6));
		Choice<Picker<String>> choice = new Choice<Picker<String>>(new RandomFloat());
		choice.add(bp1, 0.55);
		choice.add(bp2, 0.45);
		Knit<String> ip = new Knit<String>(choice, new RandomBoolean(0.5), new RandomBoolean(0.85), new RandomFloat());
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
