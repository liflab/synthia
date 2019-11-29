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
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that returns the first object fetched from another picker and repeats
 * it endlessly. For example, the following piece of code instantiates a
 * {@link ca.uqac.lif.synthia.random.RandomFloat RandomFloat}
 * <tt>r</tt>; the <tt>Freeze</tt> picker wraps around it, and keeps returning 
 * the first random float returned by <tt>r</tt>.
 * <pre>
 * RandomFloat r = new RandomFloat();
 * Freeze&lt;Float&gt; f = new Freeze&lt;Float&gt;(r);
 * float f1 = f.pick(); // 0.8104950, for example
 * float f2 = f.pick(); // 0.8104950 again
 * float f3 = f.pick(); // 0.8104950 again
 * ...
 * </pre>
 * @param <T> The type of object to pick
 */
public class Freeze<T> implements Picker<T>
{
	/**
	 * The internal picker that is to be called
	 */
	/*@ non_null @*/ protected Picker<T> m_innerPicker;
	
	/**
	 * The "frozen" value
	 */
	protected T m_value = null;
	
	/**
	 * Creates a new freeze picker
	 * @param picker The underlying picker used to get the first
	 * value
	 */
	public Freeze(/*@ non_null @*/ Picker<T> picker)
	{
		super();
		m_innerPicker = picker;
	}
	
	@Override
	public void reset()
	{
		m_value = null;
		m_innerPicker.reset();
	}

	@Override
	public T pick()
	{
		if (m_value == null)
		{
			m_value = m_innerPicker.pick();
		}
		return m_value;
	}

	@Override
	/*@ non_null @*/ public Freeze<T> duplicate(boolean with_state) 
	{
		Freeze<T> fp = new Freeze<T>(m_innerPicker.duplicate(with_state));
		if (with_state)
		{
			fp.m_value = m_value;
		}
		return fp;
	}

}
