package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

public class AllIntegers implements EnumerativePicker<Integer>
{
	protected int m_min;

	protected int m_max;

	protected int m_actualValue;

	private AllIntegers(int min, int max, int actual_value)
	{
		m_min = min;
		m_max = max;
		m_actualValue = actual_value;
	}

	public AllIntegers(int min, int max)
	{
		m_min = min;
		m_max = max;
		m_actualValue = min;
	}


	@Override public boolean isDone()
	{
		return m_actualValue > m_max;
	}

	@Override public void reset()
	{
		m_actualValue = m_min;
	}

	@Override public Integer pick()
	{
		if (isDone())
		{
			throw new NoMoreElementException();
		}

		int picked_value = m_actualValue;
		m_actualValue++;

		return picked_value;
	}

	@Override public Picker<Integer> duplicate(boolean with_state)
	{
		AllIntegers copy = new AllIntegers(m_min, m_max, m_actualValue);

		if (!with_state)
		{
			copy.m_actualValue = copy.m_min;
		}
		return copy;
	}
}
