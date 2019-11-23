package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.synthia.Picker;

/**
 * Provider that returns the same object every time  
 */
public class Constant<T> implements Picker<T>
{
	/**
	 * The value to return
	 */
	/*@ non_null @*/ protected T m_value;
	
	public Constant(/*@ non_null @*/ T value)
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
	public Constant<T> duplicate(boolean with_state)
	{
		return new Constant<T>(m_value);
	}
	
	@Override
	public String toString()
	{
		return m_value.toString();
	}
}
