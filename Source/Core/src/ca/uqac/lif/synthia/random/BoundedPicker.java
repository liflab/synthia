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
package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.exception.CannotShrinkException;
import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;

/**
 * Return the value picked by a picker a defined number of times.
 */
public class BoundedPicker<T> implements Shrinkable<T>
{
	/**
	 * A tick picker user to remember the length of the output to produce.
	 */
	protected int m_length;

	/**
	 * The number of objects produced so far.
	 */
	protected int m_outputCount;

	/**
	 * The source of values.
	 */
	/*@ non_null @*/ protected Picker<T> m_provider;

	/**
	 * Private constructor used for the duplication of the picker.
	 * @param provider The source of values.
	 * @param outputs_left The picker used to determine the number of remaining objects to produce.
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, Picker<Integer> length)
	{
		this(provider, length.pick());
	}

	/**
	 * Constructor using an integer to define the number of outputs to produce.
	 * @param provider The picker used to pick the values returned by the bounded picker.
	 * @param length A defined number of outputs to produce
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, int length)
	{
		super();
		m_provider = provider;
		m_length = length;
		m_outputCount = 0;
	}

	@Override
	public void reset()
	{
		m_provider.reset();
		m_outputCount = 0;
	}

	/**
	 * Signals if the bounded picker picked all objects from m_provider.
	 * Loop attributes must be false. If loop is true, the method will always return false.
	 * @return true if the picker picked all objects from m_provider and false if it's not the case.
	 */
	public boolean isDone()
	{
		return m_outputCount >= m_length;
	}

	@Override
	public T pick()
	{
		if (m_outputCount < m_length)
		{
			m_outputCount++;
			return m_provider.pick();
		}
		throw new NoMoreElementException();
	}

	/**
	 * Creates a copy of the bounded picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the bounded picker
	 */
	@Override
	/*@ non_null @*/ public BoundedPicker<T> duplicate(boolean with_state)
	{
		BoundedPicker<T> copy = new BoundedPicker<T>(m_provider.duplicate(with_state), m_length);
		if (with_state)
		{
			copy.m_outputCount =  m_outputCount;
		}
		return copy;
	}

	@Override
	public Shrinkable<T> shrink(T o)
	{
		if (!(m_provider instanceof Shrinkable))
		{
			throw new CannotShrinkException(m_provider);
		}
		return new BoundedPicker<T>(((Shrinkable<T>) m_provider).shrink(o), m_length);
	}
}
