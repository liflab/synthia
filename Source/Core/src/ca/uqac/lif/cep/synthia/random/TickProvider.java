package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

public class TickProvider implements Picker<Number>
{
	protected float m_startValue;
	
	protected float m_currentValue;

	protected Picker<Number> m_increment;
	
	public TickProvider(Number start, Picker<Number> increment)
	{
		super();
		m_startValue = start.floatValue();
		m_increment = increment;
		m_currentValue = m_startValue;
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
	public TickProvider duplicate(boolean with_state) 
	{
		TickProvider tp = new TickProvider(m_startValue, m_increment);
		if (with_state)
		{
			tp.m_currentValue = m_currentValue;
		}
		return tp;
	}
}
