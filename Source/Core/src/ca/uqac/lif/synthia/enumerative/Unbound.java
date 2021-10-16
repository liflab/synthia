package ca.uqac.lif.synthia.enumerative;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

public class Unbound<T> implements Picker<T>
{
	/*@ non_null @*/ protected Picker<T> m_source;
	
	/*@ non_null @*/ protected Picker<Float> m_floatSource;
	
	/*@ non_null @*/ protected List<T> m_previousValues;
	
	protected boolean m_sourceDone;
	
	public Unbound(/*@ non_null @*/ Picker<T> source, Picker<Float> float_source)
	{
		super();
		m_source = source;
		m_floatSource = float_source;
		m_previousValues = new ArrayList<T>();
		m_sourceDone = false;
	}

	@Override
	public void reset()
	{
		m_previousValues.clear();
		m_source.reset();
		m_floatSource.reset();
	}

	@Override
	public T pick()
	{
		if (m_sourceDone)
		{
			return pickFromList();
		}
		try
		{
			T t = m_source.pick();
			if (!m_previousValues.contains(t))
			{
				m_previousValues.add(t);
			}
			return t;
		}
		catch (NoMoreElementException e)
		{
			m_sourceDone = true;
			return pickFromList();
		}
	}
	
	protected T pickFromList()
	{
		if (m_previousValues.isEmpty())
		{
			throw new NoMoreElementException();
		}
		int index = (int) Math.floor((float) m_previousValues.size() * m_floatSource.pick());
		return m_previousValues.get(index);
	}

	@Override
	public Unbound<T> duplicate(boolean with_state)
	{
		Unbound<T> u = new Unbound<T>(m_source.duplicate(with_state), m_floatSource.duplicate(with_state));
		if (with_state)
		{
			u.m_sourceDone = m_sourceDone;
			u.m_previousValues.addAll(m_previousValues);
		}
		return u;
	}
}
