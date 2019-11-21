package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

public class BoundedProvider<T> implements Picker<T>
{
	protected Picker<Integer> m_intSource;
	
	/*@ non_null @*/ protected Picker<T> m_provider;
	
	protected int m_outputsLeft;
	
	public BoundedProvider(/*@ non_null @*/ Picker<T> provider, Picker<Integer> int_source, int length)
	{
		super();
		m_provider = provider;
		m_intSource = int_source;
		m_outputsLeft = length;
	}
	
	public BoundedProvider(/*@ non_null @*/ Picker<T> provider, Picker<Integer> int_source)
	{
		this(provider, int_source, int_source.pick());
	}

	@Override
	public void reset() 
	{
		m_provider.reset();
		m_intSource.reset();
	}

	@Override
	public T pick() 
	{
		if (m_outputsLeft <= 0)
		{
			return null;
		}
		m_outputsLeft--;
		return m_provider.pick();
	}

	@Override
	/*@ non_null @*/ public BoundedProvider<T> duplicate(boolean with_state)
	{
		BoundedProvider<T> bp = new BoundedProvider<T>(m_provider.duplicate(with_state), m_intSource);
		if (with_state) 
		{
			bp.m_outputsLeft = m_outputsLeft;
		}
		return bp;
	}
}
