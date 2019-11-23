package ca.uqac.lif.cep.synthia.replay;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;

public class Record<T> implements Picker<T>
{
	/*@ non_null @*/ protected Picker<T> m_picker;
	
	/*@ non_null @*/ protected List<T> m_values;
	
	public Record(/*@ non_null @*/ Picker<T> picker)
	{
		super();
		m_picker = picker;
		m_values = new ArrayList<T>();
	}

	@Override
	public void reset() 
	{
		m_picker.reset();
		m_values.clear();
	}

	@Override
	public T pick() 
	{
		T value = m_picker.pick();
		m_values.add(value);
		return value;
	}

	@Override
	/*@ pure non_null @*/ public Picker<T> duplicate(boolean with_state) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/*@ pure @*/ public int getCount()
	{
		return m_values.size();
	}
	
	/*@ pure non_null @*/ public List<T> getValues()
	{
		return m_values;
	}
}
