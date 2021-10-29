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

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.relative.NothingPicker;

/**
 * Picker that returns the same object every time. For example, the following
 * code snippet will create an object that will return the string "foo" every
 * time its {@link #pick()} method is called:
 * <pre>
 * Constant&lt;String&gt; c = new Constant&lt;String&gt;("foo");
 * String s1 = c.pick(); // "foo"
 * String s2 = c.pick(); // "foo"
 * ...
 * </pre>
 * @ingroup API
 */
public class Constant<T> implements Shrinkable<T>
{
	/**
	 * The value to return
	 */
	/*@ non_null @*/ protected T m_value;
	
	/**
	 * Creates a new constant
	 * @param value The value to be returned on every call to {@link #pick()}
	 */
	public Constant(/*@ non_null @*/ T value)
	{
		super();
		m_value = value;
	}

	/**
	 * Picks the constant value.
	 * @return The constant value.
	 */
	@Override
	public T pick() 
	{
		return m_value;
	}


	/**
	 * Puts the constant back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset() 
	{
		// Nothing to do
		
	}


	/**
	 * Creates a copy of the constant picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the constant picker
	 */
	@Override
	public Constant<T> duplicate(boolean with_state)
	{
		return new Constant<>(m_value);
	}


	/**
	 * Returns the constant value into a string.
	 * @return The string who contains the constant value.
	 */
	@Override
	public String toString()
	{
		return m_value.toString();
	}

	@Override
	public Shrinkable<T> shrink(T o)
	{
		return new NothingPicker<T>();
	}
}
