package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

/**
 * Picker who implements EnumerativePicker. This picker enumerates the integer values
 * from x to y and throws a {@link ca.uqac.lif.synthia.NoMoreElementException} if the picker picks
 * another value after the picker has finished to enumerates the values.
 */
public class AllIntegers implements EnumerativePicker<Integer>
{
	/**
	 * The minimal value the picker can generate.
	 */
	protected int m_min;

	/**
	 * The maximal value the picker can generate.
	 */
	protected int m_max;

	/**
	 * The actual value to generate.
	 */
	protected int m_actualValue;

	/**
	 * Private constructor used to duplicate the picker.
	 * @param min The m_min value to copy in a new AllIntegers instance.
	 * @param max The m_max value to copy in a new AllIntegers instance.
	 * @param actual_value The m_actualValue value to copy in a new AllIntegers instance.
	 */
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

	@Override
	public boolean isDone()
	{
		return m_actualValue > m_max;
	}

	@Override
	public void reset()
	{
		m_actualValue = m_min;
	}

	@Override
	public Integer pick()
	{
		if (isDone())
		{
			throw new NoMoreElementException();
		}

		int picked_value = m_actualValue;
		m_actualValue++;

		return picked_value;
	}

	@Override
	public Picker<Integer> duplicate(boolean with_state)
	{
		AllIntegers copy = new AllIntegers(m_min, m_max, m_actualValue);

		if (!with_state)
		{
			copy.m_actualValue = copy.m_min;
		}
		return copy;
	}
}
