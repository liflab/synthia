package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

/**
 * Picker who implements {@link EnumerativePicker}. This picker enumerates all the possibility of
 * of combinaisons of picked value from an array of Enumerative pickers. For example, an
 * AllPickers containing an array of 2 {@link AllBooleans} will generates one array
 * in the following order :
 * <ol>
 *   <li>[<Boolean>false</Boolean>, <Boolean>false</Boolean>]</li>
 *   <li>[<Boolean>true</Boolean>, <Boolean>false</Boolean>]</li>
 *   <li>[<Boolean>false</Boolean>, <Boolean>true</Boolean>]</li>
 *   <li>[<Boolean>true</Boolean>, <Boolean>true</Boolean>]</li>
 * </ol>
 * After that, the picker will throw a {@link NoMoreElementException} if the pick method is called
 * one more time.
 */
public class AllPickers implements EnumerativePicker
{
	/**
	 * The array of pickers used to generate all the possible combinations.
	 */
	protected EnumerativePicker[] m_enumPickers;

	/**
	 * Flag to check if it's the first pick.
	 */
	protected boolean m_firstPick;

	/**
	 * Flag to check if the picker finished generating objects.
	 */
	protected boolean m_done;

	/**
	 * An array to store the combination to return.
	 */
	protected Object[] m_values;

	/**
	 * Private constructor used to duplicate the picker.
	 * @param enum_pickers The m_enumPickers attribute of the AllPickers instance to duplicate.
	 * @param first_pick The m_firstPick attribute of the AllPickers instance to duplicate.
	 * @param values The m_values attribute of the AllPickers instance to duplicate.
	 * @param done The m_done attribute of the AllPickers instance to duplicate.
	 */
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

	/**
	 * Private method to generate a combination of values from the array of pickers.
	 * This private method is to simplify the pick public method.
	 */
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

	/**
	 * Private method used by the private pick method to check if at least one more object can be
	 * generated.
	 * @return <tt>true</tt> if the picker can still generate at least one more object and
	 * <tt>false</tt> if it's not the case.
	 */
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

	/**
	 * Private method used to generate the first combination of value from the
	 * array of EnumarativePickers.
	 */
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
