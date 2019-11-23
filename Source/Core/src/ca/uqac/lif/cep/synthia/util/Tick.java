package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.cep.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.Picker;

public class Tick implements Picker<Number>
{
	protected float m_startValue;
	
	protected float m_currentValue;

	protected Picker<? extends Number> m_increment;
	
	public Tick(Number start, Picker<? extends Number> increment)
	{
		super();
		m_startValue = start.floatValue();
		m_increment = increment;
		m_currentValue = m_startValue;
	}
	
	public Tick(Picker<? extends Number> increment)
	{
		this(0, increment);
	}
	
	public Tick()
	{
		this(0, new RandomFloat());
	}

	@Override
	public void reset()
	{
		m_currentValue = m_startValue;
	}

	@Override
	public Number pick() 
	{
		m_currentValue += m_increment.pick().floatValue();
		return m_currentValue;
	}

	@Override
	public Tick duplicate(boolean with_state) 
	{
		Tick tp = new Tick(m_startValue, m_increment);
		if (with_state)
		{
			tp.m_currentValue = m_currentValue;
		}
		return tp;
	}
}
