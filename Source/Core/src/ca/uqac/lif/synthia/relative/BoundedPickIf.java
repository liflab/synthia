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
package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Bounded;
import ca.uqac.lif.synthia.NoMoreElementException;

/**
 * Variant of {@link PickIf} that accepts and behaves like a {@link Bounded}
 * picker.
 *
 * @param <T> The object type returned by the picker.
 * @ingroup API
 */
public class BoundedPickIf<T> extends PickIf<T> implements Bounded<T>
{
	/**
	 * The next element to produce on a call to {@link #pick()}.
	 */
	protected T m_next;
	
	/**
	 * Constructor with default {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 */
	public BoundedPickIf(Bounded<? extends T> picker)
	{
		super(picker);
		m_next = null;
	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public BoundedPickIf(Bounded<? extends T> picker, int max_iteration)
	{
		super(picker, max_iteration);
		m_next = null;
	}
	
	@Override
	public T pick()
	{
	  if (m_next == null)
	  {
	    if (isDone())
	    {
	      throw new NoMoreElementException();
	    }
	  }
	  T o = m_next;
	  m_next = null;
	  return o;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BoundedPickIf<T> duplicate(boolean with_state)
	{
		BoundedPickIf<T> pif = new BoundedPickIf<T>((Bounded<T>) m_picker.duplicate(with_state), m_maxIteration);
		copyInto(pif, with_state);
		return pif;
	}

	@Override
	public void reset()
	{
		super.reset();
		m_next = null;
	}
	
	@Override
	public String toString()
	{
		return "BoundedPickIf";
	}

	@Override
	public boolean isDone()
	{
		if (m_next != null)
		{
			return false;
		}
		while (m_next == null && !((Bounded<?>) m_picker).isDone())
		{
		  T o = m_picker.pick();
		  if (select(o))
		  {
		    m_next = o;
		    break;
		  }
		}
		if (m_next == null)
		{
		  return true;
		}
		return false;
	}
	
	protected void copyInto(BoundedPickIf<T> pi, boolean with_state)
	{
		super.copyInto(pi, with_state);
		if (with_state)
		{
			pi.m_next = m_next;
		}
	}
}
