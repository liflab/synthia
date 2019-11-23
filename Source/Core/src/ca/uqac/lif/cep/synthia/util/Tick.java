package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.cep.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.Picker;

public class Tick implements Picker<Number>
{
	protected Picker<? extends Number> m_startValue;
	
	protected float m_currentValue;

	protected Picker<? extends Number> m_increment;
	
	public Tick(Picker<? extends Number> start, Picker<? extends Number> increment)
	{
		super();
		m_startValue = start;
		m_increment = increment;
		m_currentValue = m_startValue.pick().floatValue();
	}
	
	public Tick(Number start, Number increment)
	{
		this(new Constant<Number>(start), new Constant<Number>(increment));
	}
	
	public Tick(Number start, Picker<? extends Number> increment)
	{
		this(new Constant<Number>(start), increment);
	}
	
	public Tick(Picker<? extends Number> increment)
	{
		this(new Constant<Number>(0), increment);
	}
	
	public Tick()
	{
		this(new Constant<Number>(0), new RandomFloat());
	}

	@Override
	public void reset()
	{
		m_startValue.reset();
		m_increment.reset();
		m_currentValue = m_startValue.pick().floatValue();
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
