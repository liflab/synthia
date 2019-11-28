package ca.uqac.lif.cep.synthia.util;

import ca.uqac.lif.synthia.Picker;

public class Once<T> implements Picker<T>
{
	/*@ non_null @*/ Picker<T> m_picker;
	
	boolean m_picked;
	
	public Once(/*@ non_null @*/ Picker<T> picker)
	{
		super();
		m_picker = picker;
		m_picked = false;
	}
	
	@Override
	public void reset() 
	{
		m_picked = false;
		m_picker.reset();
		
	}

	@Override
	public T pick() 
	{
		if (m_picked)
		{
			return null;
		}
		m_picked = true;
		return m_picker.pick();
	}

	@Override
	public Once<T> duplicate(boolean with_state)
	{
		Once<T> o = new Once<T>(m_picker.duplicate(with_state));
		if (with_state)
		{
			o.m_picked = m_picked;
		}
		return o;
	}
}
