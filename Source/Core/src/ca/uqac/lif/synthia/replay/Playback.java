/*
    Synthia, a data structure generator
    Copyright (C) 2019-2020 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.replay;

import java.util.List;

import ca.uqac.lif.synthia.enumerative.EnumerativePicker;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

//TODO check constructors whit a list as parameter for m_values
/**
 * Picker that returns values taken from a list. As its name implies,
 * <tt>Playback</tt> literally replays the values fetched from a list that
 * was passed to its constructor. It loops back to the beginning when the list
 * is over (this can optionally be disabled).
 * <pre>
 * Playback&lt;Integer&gt; p = new Playback&lt;Integer&gt;(2, 7, 1);
 * System.out.println(p.pick()); // 2
 * System.out.println(p.pick()); // 7
 * System.out.println(p.pick()); // 1
 * System.out.println(p.pick()); // 2
 * ...</pre>
 * Optionally, the picker can be told to start at a different element than the
 * first one. By default, the picker keeps outputting the last value of the
 * sequence if more values are requested and the <tt>loop</tt> parameter is
 * set to false.
 * @param <T> The type of objects to return
 */
public class Playback<T> implements EnumerativePicker<T>, Picker<T>
{
	/**
	 * The values to play back
	 */
	/*@ non_null @*/ protected T[] m_values;

	/**
	 * The index of the current value
	 */
	protected int m_index;

	/**
	 * The start index
	 */
	protected int m_startIndex;

	/**
	 * Whether to loop through the values
	 */
	protected boolean m_loop;

	/**
	 * Creates a new Playback picker
	 * @param start_index The position of the first value to return
	 * @param values The values to play back
	 */
	@SuppressWarnings("unchecked")
	public Playback(int start_index, /*@ non_null @*/ T ... values)
	{
		m_values = values;
		m_index = start_index;
		m_startIndex = start_index;
		m_loop = true;
	}

	/**
	 * Set the m_loop attribute of the playback picker. If the attribute is set to false, the next call to
	 * the {@link #pick()} method will return a {@link ca.uqac.lif.synthia.NoMoreElementException} after
	 * producing the last element of the list.
	 * @param m_loop boolean value to enable playback loop or not
	 * @return
	 */
	public Playback<T> setM_loop(boolean m_loop) {
		this.m_loop = m_loop;
		return this;
	}

	/**
	 * Creates a new Playback picker
	 * @param values The values to play back
	 */
	@SuppressWarnings("unchecked")
	public Playback(/*@ non_null @*/ T ... values)
	{
		this(0, values);
	}

	/**
	 * Creates a new Playback picker
	 * @param start_index The position of the first value to return
	 * @param values The values to play back
	 */
	@SuppressWarnings("unchecked")
	public Playback(int start_index, /*@ non_null @*/ List<T> values)
	{
		m_values = (T[]) values.toArray();
		m_index = start_index;
		m_startIndex = start_index;
	}

	/**
	 * Creates a new Playback picker
	 * @param values The values to play back
	 */
	public Playback(/*@ non_null @*/ List<T> values)
	{
		this(0, values);
	}



	/**
	 * Picks the next value in the list of the Playback picker. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The next value
	 */
	@Override
	public T pick()
	{
		if (m_index >= m_values.length && !m_loop)
		{
			throw new NoMoreElementException();
		}
		T f = m_values[m_index];
		m_index++;
		if (m_index == m_values.length && m_loop)
		{
			m_index = 0;
		}
		return f;
	}


	/**
	 * Puts the Playback picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset()
	{
		m_index = 0;
	}


	/**
	 * Picks a random string. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random string.
	 */
	@Override
	public Playback<T> duplicate(boolean with_state)
	{
		Playback<T> lp = new Playback<T>(m_startIndex, m_values);
		if (with_state)
		{
			lp.m_index = m_index;
		}
		if (!this.m_loop){
			lp.setM_loop(false);
		}
		return lp;
	}


	/**
	 * Sets whether to loop through the list of values or play them back
	 * only once.
	 * @param b Set to <tt>true</tt> to loop through values, <tt>false</tt>
	 * otherwise
	 * @return This object
	 */
	public Playback<T> loop(boolean b)
	{
		m_loop = b;
		return this;
	}

	/**
	 * Signals if the playback picker enumerates all values from m_values.
	 * Loop attributes must be false. If loop is true, the method will always return false.
	 * @return true if the picker enumerates all values of m_values and false if it's not the case.
	 */
	@Override
	public boolean isDone()
	{
		return (m_index >= (m_values.length)) && !m_loop;
	}
}
