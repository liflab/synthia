package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.synthia.Picker;

public class Enumerate<T> implements Picker<T>
{
	/*@ non_null @*/ protected T[] m_values;
	
	/*@ non_null @*/ protected float[] m_probabilities;
	
	protected int m_index;
	
	@SuppressWarnings("unchecked")
	public Enumerate(/*@ non_null @*/ T ... values)
	{
		m_values = values;
		m_index = 0;
	}
	@Override
	public T pick()
	{
		T f = m_values[m_index];
		m_index = (m_index + 1) % m_values.length;
		return f;
	}
	
	@Override
	public void reset() 
	{
		m_index = 0;
	}
	
	@Override
	public Enumerate<T> duplicate(boolean with_state)
	{
		Enumerate<T> lp = new Enumerate<T>(m_values);
		if (with_state)
		{
			lp.m_index = m_index;
		}
		return lp;
	}
}
