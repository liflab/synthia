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

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

/**
 * Picker that returns the first value fetched from another picker, and
 * then <tt>null</tt> afterwards. For example, the following piece of
 * code instantiates a
 * {@link ca.uqac.lif.synthia.random.RandomFloat RandomFloat}
 * <tt>r</tt>; the <tt>Once</tt> picker wraps around it, returns the
 * first random float returned by <tt>r</tt>, and then <tt>null</tt> on
 * subsequent calls.
 * <pre>
 * RandomFloat r = new RandomFloat();
 * Once&lt;Float&gt; f = new Once&lt;Float&gt;(r);
 * float f1 = f.pick(); // 0.8104950, for example
 * float f2 = f.pick(); // null
 * float f3 = f.pick(); // null again
 * ...</pre>
 * Note that a call to {@link #reset()} will cause the picker to return a
 * non-null object on its next call to {@link #pick()}.
 * 
 * @param <T> The type of object to pick
 * @ingroup API
 */
public class Once<T> implements Picker<T>
{
	/**
	 * The internal picker that is to be called
	 */
	/*@ non_null @*/ Picker<T> m_picker;
	
	/**
	 * A flag indicating whether <tt>m_picker</tt> has been called once
	 */
	boolean m_picked;
	
	/**
	 * Creates a new Once picker.
	 * @param picker The underlying picker used to get the first
	 * value
	 */
	public Once(/*@ non_null @*/ Picker<T> picker)
	{
		super();
		m_picker = picker;
		m_picked = false;
	}


	/**
	 * Puts the once picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset() 
	{
		m_picked = false;
		m_picker.reset();
		
	}


	/**
	 * Picks a random value. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random string.
	 */
	@Override
	public T pick() 
	{
		if (m_picked)
		{
			throw new NoMoreElementException();
		}
		m_picked = true;
		return m_picker.pick();
	}


	/**
	 * Creates a copy of the once picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomString picker
	 */
	@Override
	public Once<T> duplicate(boolean with_state)
	{
		Once<T> o = new Once<>(m_picker.duplicate(with_state));
		if (with_state)
		{
			o.m_picked = m_picked;
		}
		return o;
	}
}
