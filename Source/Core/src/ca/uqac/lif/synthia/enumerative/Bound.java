package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.util.Constant;

public class Bound<T> implements Bounded<T>
{
	/*@ non_null @*/ protected Picker<T> m_source;
	
	/*@ non_null @*/ protected Picker<Integer> m_length;
	
	protected int m_chosenLength;
	
	protected int m_currentLength;
	
	public Bound(Picker<T> source, Picker<Integer> length)
	{
		super();
		m_source = source;
		m_length = length;
		m_chosenLength = m_length.pick();
		m_currentLength = 0;
	}
	
	public Bound(Picker<T> source, int length)
	{
		this(source, new Constant<Integer>(length));
	}
	
	@Override
	public void reset()
	{
		m_source.reset();
		m_length.reset();
		m_currentLength = 0;
	}

	@Override
	public T pick()
	{
		if (m_chosenLength == m_currentLength)
		{
			throw new NoMoreElementException();
		}
		m_chosenLength++;
		return m_source.pick();
	}

	@Override
	public Bound<T> duplicate(boolean with_state)
	{
		Bound<T> b = new Bound<T>(m_source.duplicate(with_state), m_length.duplicate(with_state));
		if (with_state)
		{
			b.m_chosenLength = m_chosenLength;
			b.m_currentLength = m_currentLength;
		}
		return b;
	}

	@Override
	public boolean isDone()
	{
		return m_chosenLength == m_currentLength;
	}
}
