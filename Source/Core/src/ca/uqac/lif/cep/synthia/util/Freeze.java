package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.synthia.Picker;

public class Freeze<T> implements Picker<T>
{
	/*@ non_null @*/ protected Picker<T> m_innerProvider;
	
	protected T m_value = null;
	
	public Freeze(/*@ non_null @*/ Picker<T> provider)
	{
		super();
		m_innerProvider = provider;
	}
	
	@Override
	public void reset()
	{
		m_value = null;
		m_innerProvider.reset();
	}

	@Override
	public T pick()
	{
		if (m_value == null)
		{
			m_value = m_innerProvider.pick();
		}
		return m_value;
	}

	@Override
	/*@ non_null @*/ public Freeze<T> duplicate(boolean with_state) 
	{
		Freeze<T> fp = new Freeze<T>(m_innerProvider.duplicate(with_state));
		if (with_state)
		{
			fp.m_value = m_value;
		}
		return fp;
	}

}
