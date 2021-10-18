/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
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
import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.CannotShrinkException;
import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.util.Constant;

/**
 * Return the value picked by a picker a defined number of times.
 */
public class Bound<T> extends Mutator<T> implements Bounded<T>, Shrinkable<T>
{
	/*@ non_null @*/ protected Picker<Integer> m_length;
	
	protected int m_chosenLength;
	
	protected int m_currentLength;
	
	public Bound(Picker<T> source, Picker<Integer> length)
	{
		super(source);
		m_length = length;
		m_chosenLength = m_length.pick();
		m_currentLength = 0;
	}
	
	public Bound(Picker<T> source, int length)
	{
		this(source, new Constant<Integer>(length));
	}
	
	@Override
	public void reset()
	{
		m_picker.reset();
		m_length.reset();
		m_currentLength = 0;
	}

	@Override
	public T pick()
	{
		if (m_chosenLength == m_currentLength)
		{
			throw new NoMoreElementException();
		}
		m_chosenLength++;
		return m_picker.pick();
	}

	@Override
	public Bound<T> duplicate(boolean with_state)
	{
		Bound<T> b = new Bound<T>(m_picker.duplicate(with_state), m_length.duplicate(with_state));
		if (with_state)
		{
			b.m_chosenLength = m_chosenLength;
			b.m_currentLength = m_currentLength;
		}
		return b;
	}

	@Override
	public boolean isDone()
	{
		return m_chosenLength == m_currentLength;
	}
	
	@Override
	public Shrinkable<T> shrink(T o)
	{
		if (!(m_picker instanceof Shrinkable))
		{
			throw new CannotShrinkException(m_picker);
		}
		return new Bound<T>(((Shrinkable<T>) m_picker).shrink(o), m_length);
	}
}
