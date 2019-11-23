package ca.uqac.lif.cep.synthia.replay;

import java.util.List;

import ca.uqac.lif.synthia.Picker;

public class Playback<T> implements Picker<T>
{
	/*@ non_null @*/ protected T[] m_values;
	
	protected int m_index;
	
	protected int m_startIndex;
	
	@SuppressWarnings("unchecked")
	public Playback(int start_index, /*@ non_null @*/ T ... values)
	{
		m_values = values;
		m_index = start_index;
		m_startIndex = start_index;
	}
	
	@SuppressWarnings("unchecked")
	public Playback(/*@ non_null @*/ T ... values)
	{
		this(0, values);
	}
	
	@SuppressWarnings("unchecked")
	public Playback(int start_index, /*@ non_null @*/ List<T> values)
	{
		m_values = (T[]) values.toArray();
		m_index = start_index;
		m_startIndex = start_index;
	}
	
	public Playback(/*@ non_null @*/ List<T> values)
	{
		this(0, values);
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
	public Playback<T> duplicate(boolean with_state)
	{
		Playback<T> lp = new Playback<T>(m_startIndex, m_values);
		if (with_state)
		{
			lp.m_index = m_index;
		}
		return lp;
	}
}
