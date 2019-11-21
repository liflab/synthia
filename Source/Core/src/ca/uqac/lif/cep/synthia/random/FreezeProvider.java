package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

public class FreezeProvider<T> implements Picker<T>
{
	/*@ non_null @*/ protected Picker<T> m_innerProvider;
	
	protected T m_value = null;
	
	public FreezeProvider(/*@ non_null @*/ Picker<T> provider)
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
	/*@ non_null @*/ public FreezeProvider<T> duplicate(boolean with_state) 
	{
		FreezeProvider<T> fp = new FreezeProvider<T>(m_innerProvider.duplicate(with_state));
		if (with_state)
		{
			fp.m_value = m_value;
		}
		return fp;
	}

}
