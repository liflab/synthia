package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

/**
 * Provider that returns the same object every time  
 */
public class StaticObjectProvider<T> implements Picker<T>
{
	/**
	 * The value to return
	 */
	/*@ non_null @*/ protected T m_value;
	
	public StaticObjectProvider(/*@ non_null @*/ T value)
	{
		super();
		m_value = value;
	}
	
	@Override
	public T pick() 
	{
		return m_value;
	}

	@Override
	public void reset() 
	{
		// Nothing to do
		
	}

	@Override
	public StaticObjectProvider<T> duplicate(boolean with_state)
	{
		return new StaticObjectProvider<T>(m_value);
	}
}
