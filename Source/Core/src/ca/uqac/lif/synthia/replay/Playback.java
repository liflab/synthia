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

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that returns values taken from a list. As its name implies,
 * <tt>Playback</tt> literally replays the values fetched from a list that
 * was passed to its constructor. It loops back to the beginning when the list
 * is over.
 * <pre>
 * Playback&lt;Integer&gt; p = new Playback&lt;Integer&gt;(2, 7, 1);
 * System.out.println(p.pick()); // 2
 * System.out.println(p.pick()); // 7
 * System.out.println(p.pick()); // 1
 * System.out.println(p.pick()); // 2
 * ...</pre>
 * Optionally, the picker can be told to start at a different element than the
 * first one.
 * @param <T> The type of objects to return
 */
public class Playback<T> implements Picker<T>
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
	public Playback<T> duplicate(boolean with_state)
	{
		Playback<T> lp = new Playback<T>(m_startIndex, m_values);
		if (with_state)
		{
			lp.m_index = m_index;
		}
		return lp;
	}
}
