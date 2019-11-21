package ca.uqac.lif.synthia;

public class ListPicker<T> implements Picker<T>
{
	/*@ non_null @*/ protected T[] m_values;
	
	protected int m_index;
	
	@SuppressWarnings("unchecked")
	public ListPicker(/*@ non_null @*/ T ... values)
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
	public ListPicker<T> duplicate(boolean with_state)
	{
		ListPicker<T> lp = new ListPicker<T>(m_values);
		if (with_state)
		{
			lp.m_index = m_index;
		}
		return lp;
	}
}
