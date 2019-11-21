package ca.uqac.lif.synthia;

import java.util.Random;

public abstract class UniformRandomPicker<T> implements Picker<T>, Seedable
{
	/*@ non_null @*/ protected transient Random m_random;
	
	protected int m_seed;
	
	public UniformRandomPicker(int seed)
	{
		super();
		setSeed(seed);
	}
	
	public UniformRandomPicker()
	{
		this(0);
	}

	@Override
	public void setSeed(int seed) 
	{
		m_seed = seed;
		m_random = new Random(seed);
	}

	@Override
	public void reset() 
	{
		m_random = new Random(m_seed);
	}
	
	public static class UniformIntervalIntegerSource extends UniformRandomPicker<Integer>
	{
		protected int m_min;
		
		protected int m_max;
		
		public UniformIntervalIntegerSource(int min, int max)
		{
			super();
			m_min = min;
			m_max = max;
		}
		
		@Override
		public Integer pick() 
		{
			return m_random.nextInt(m_max - m_min) + m_min;
		}
		
		@Override
		public UniformIntervalIntegerSource duplicate(boolean with_state)
		{
			return new UniformIntervalIntegerSource(m_min, m_max);
		}
	}
	
	public static class UniformFloatSource extends UniformRandomPicker<Float>
	{
		public UniformFloatSource()
		{
			super();
		}
		
		@Override
		public Float pick() 
		{
			return m_random.nextFloat();
		}
		
		@Override
		public UniformFloatSource duplicate(boolean with_state)
		{
			return new UniformFloatSource();
		}
	}
	
	public static class UniformIntervalFloatSource extends UniformRandomPicker<Float>
	{
		protected float m_min;
		
		protected float m_max;
		
		public UniformIntervalFloatSource(Number min, Number max)
		{
			super();
			m_min = min.floatValue();
			m_max = max.floatValue();
		}
		
		@Override
		public Float pick() 
		{
			return m_random.nextFloat() * (m_max - m_min) + m_min;
		}
		
		@Override
		public UniformIntervalFloatSource duplicate(boolean with_state)
		{
			return new UniformIntervalFloatSource(m_min, m_max);
		}
	}
}
