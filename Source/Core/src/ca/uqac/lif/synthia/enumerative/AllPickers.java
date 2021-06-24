package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

public class AllPickers implements EnumerativePicker
{
	protected EnumerativePicker[] m_enumPickers;

	protected boolean m_firstPick;

	protected boolean m_done;

	protected Object[] m_values;


	private AllPickers(EnumerativePicker[] enum_pickers, boolean first_pick, Object[] values
			, boolean done)
	{
		m_enumPickers = enum_pickers;
		m_firstPick = first_pick;
		m_values = values;
		m_done = done;
	}

	public AllPickers(EnumerativePicker[] enum_pickers)
	{
		m_enumPickers = enum_pickers;
		m_firstPick = true;
		m_values = new Object[m_enumPickers.length];
		m_done = false;
	}

	@Override
	public boolean isDone()
	{
		return m_done;
	}

	@Override
	public void reset()
	{
		m_firstPick = true;
		m_values = new Object[m_enumPickers.length];
		m_done = false;

		for (EnumerativePicker m_enumPicker : m_enumPickers)
		{
			m_enumPicker.reset();
		}
	}

	@Override
	public Object[] pick()
	{

		if (m_firstPick)
		{
			firstPick();
			return m_values;
		}

		if (isDone())
		{
			throw new NoMoreElementException();
		}

		internalPick();
		return m_values;
	}

	private void internalPick()
	{
		if (!m_enumPickers[0].isDone())
		{
			m_values[0] = m_enumPickers[0].pick();
			m_done = internalIsDone();
		}
		else
		{
			int i =0;
			while (m_enumPickers[i].isDone())
			{
				i++;
			}

			if (i < m_enumPickers.length)
			{
				for (int j = 0; j < i; j++)
				{
					m_enumPickers[j].reset();
					m_values[j] = m_enumPickers[j].pick();
				}
				m_values[i] = m_enumPickers[i].pick();

				m_done = internalIsDone();

			}
			else
			{
				throw new NoMoreElementException();
			}

		}
	}

	private boolean internalIsDone()
	{
		int counter = 0;
		for (int i = 0; i < m_enumPickers.length; i++)
		{
			if (m_enumPickers[i].isDone())
			{
				counter++;
			}
		}
		return counter == m_enumPickers.length;
	}

	private void firstPick()
	{
		for (int i = 0; i < m_enumPickers.length; i++)
		{
			m_values[i] = m_enumPickers[i].pick();
		}
		m_firstPick = false;
	}

	@Override
	public Picker duplicate(boolean with_state)
	{
		EnumerativePicker[] enum_picker_copy = new EnumerativePicker[m_enumPickers.length];
		Object[] values_copy = new Object[m_enumPickers.length];

		for (int i = 0; i < m_enumPickers.length; i++)
		{
			enum_picker_copy[i] = (EnumerativePicker) m_enumPickers[i].duplicate(with_state);
			values_copy[i] = m_values[i];
		}

		AllPickers copy = new AllPickers(enum_picker_copy, m_firstPick, values_copy, m_done);

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}
}
