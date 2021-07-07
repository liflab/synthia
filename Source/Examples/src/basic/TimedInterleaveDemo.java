package basic;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomString;
import ca.uqac.lif.synthia.sequence.TimedInterleave;
import ca.uqac.lif.synthia.sequence.TimedInterleave.StartSettable;
import ca.uqac.lif.synthia.util.Freeze;
import ca.uqac.lif.synthia.util.StringPattern;
import ca.uqac.lif.synthia.util.Tick;

public class TimedInterleaveDemo 
{
	public static void main(String[] args)
	{
		Tick tick = new Tick(new RandomFloat(0, 10), new RandomFloat(0.5, 10));
		SimpleTimedInterleave ti = new SimpleTimedInterleave(new RandomFloat(), 0.5, 0.4);
		Picker<String> session = new TimedSession(tick);
		ti.add(session, 1);
		for (int i = 0; i < 20; i++)
		{
			System.out.println(ti.pick());
		}
	}
	
	public static class SimpleTimedInterleave extends TimedInterleave<String>
	{
		public SimpleTimedInterleave(Picker<Float> float_source, Number mid_probability, Number end_probability) 
		{
			super(float_source, mid_probability, end_probability);
		}
	
		@Override
		public float getTimestamp(String s)
		{
			String[] parts = s.split(",");
			return Float.parseFloat(parts[0]);
		}
	}
	
	public static class TimedSession implements StartSettable<String>
	{
		protected Tick m_tick;
		
		protected StringPattern m_pattern;
		
		public TimedSession(Tick t)
		{
			super();
			m_tick = t;
			m_pattern = new StringPattern("{$0},{$1} - {$2}",
					m_tick,
					new Freeze<String>(new RandomString(8)),
					new RandomString(8));
		}
		
		@Override
		public void reset()
		{
			m_tick.reset();
			m_pattern.reset();
		}

		@Override
		public String pick()
		{
			return m_pattern.pick();
		}

		@Override
		public TimedSession duplicate(boolean with_state) 
		{
			return duplicate(with_state, 0);
		}

		@Override
		public TimedSession duplicate(boolean with_state, float start_timestamp) 
		{
			TimedSession ts = new TimedSession(m_tick.duplicate(with_state).setValue(start_timestamp));
			return ts;
		}
	}
}
