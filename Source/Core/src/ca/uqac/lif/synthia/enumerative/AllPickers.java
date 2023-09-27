/*
    Synthia, a data structure generator
    Copyright (C) 2019-2023 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.NoMoreElementException;

/**
 * Enumerates all the
 * of combinations of picked values from an array of enumerative pickers. For example, an
 * AllPickers containing an array of 2 {@link AllBooleans} will generates one array
 * in the following order :
 * <ol>
 *   <li>[<tt>false</tt>, <tt>false</tt>]</li>
 *   <li>[<tt>true</tt>, <tt>false</tt>]</li>
 *   <li>[<tt>false</tt>, <tt>true</tt>]</li>
 *   <li>[<tt>true</tt>, <tt>true</tt>]</li>
 * </ol>
 * After that, the picker will throw a {@link NoMoreElementException} if the pick method is called
 * one more time.
 * @ingroup API
 */
public class AllPickers implements Bounded<Object[]>
{
	/**
	 * The array of pickers used to generate all the possible combinations.
	 */
	protected Bounded<?>[] m_enumPickers;

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
	private AllPickers(Bounded<?>[] enum_pickers, boolean first_pick, Object[] values
			, boolean done)
	{
		m_enumPickers = enum_pickers;
		m_firstPick = first_pick;
		m_values = values;
		m_done = done;
	}

	public AllPickers(Bounded<?>[] enum_pickers)
	{
		this(enum_pickers, true, new Object[enum_pickers.length], isOneDone(enum_pickers));
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
		for (Bounded<?> m_enumPicker : m_enumPickers)
		{
			m_enumPicker.reset();
		}
		m_done = isOneDone(m_enumPickers);
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
	
	protected static boolean isOneDone(Bounded<?>[] pickers)
	{
		for (Bounded<?> p : pickers)
		{
			if (p.isDone())
			{
				return true;
			}
		}
		return false;
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
		m_done = internalIsDone();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AllPickers duplicate(boolean with_state)
	{
		Bounded[] enum_picker_copy = new Bounded[m_enumPickers.length];
		Object[] values_copy = new Object[m_enumPickers.length];

		for (int i = 0; i < m_enumPickers.length; i++)
		{
			enum_picker_copy[i] = (Bounded<?>) m_enumPickers[i].duplicate(with_state);
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
