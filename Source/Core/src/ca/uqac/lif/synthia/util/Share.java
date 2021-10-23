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
package ca.uqac.lif.synthia.util;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.exception.NoMoreElementException;

/**
 * Allows values of a single picker to be shared among multiple copies of a
 * picker instance.
 *
 * @param <T> The type of the elements to pick
 */
public class Share<T>
{
	/**
	 * The underlying picker from which elements are taken.
	 */
	protected Picker<? extends T> m_picker;
	
	/**
	 * The set of "spawned" copies created.
	 */
	protected Set<QueuePicker> m_pickers;
	
	/**
	 * Creates a new instance of the share picker.
	 * @param picker The underlying picker from which elements are taken
	 */
	public Share(Picker<? extends T> picker)
	{
		super();
		m_picker = picker;
		m_pickers = new HashSet<QueuePicker>();
	}
	
	/**
	 * Creates a new copy of {@link QueuePicker} from the original picker.
	 * @return A new copy
	 */
	public QueuePicker getCopy()
	{
		QueuePicker qp = new QueuePicker();
		m_pickers.add(qp);
		return qp;
	}
	
	/**
	 * Resets the original picker and all its copies.
	 */
	public void reset()
	{
		m_picker.reset();
		for (QueuePicker qp : m_pickers)
		{
			qp.resetInternal();
		}
	}

	/**
	 * Asks for a new value from the underlying picker, and adds this value to
	 * the internal queue of each linked instance of {@link QueuePicker}.
	 */
	protected void ask()
	{
		T e = m_picker.pick();
		for (QueuePicker qp : m_pickers)
		{
			qp.m_values.add(e);
		}
	}
	
	/**
	 * A picker that acts as a proxy for the values obtained by the parent
	 * {@link Share} picker. Each instance of {@link QueuePicker} is guaranteed
	 * to output the same values in the same order as the picker given to
	 * {@link Share} when instantiated.
	 */
	protected class QueuePicker implements Picker<T>
	{
		protected Queue<T> m_values;
		
		public QueuePicker()
		{
			super();
			m_values = new ArrayDeque<T>();
		}
		
		@Override
		public void reset()
		{
			m_picker.reset();
		}
		
		public void resetInternal()
		{
			m_values.clear();
		}

		@Override
		public T pick()
		{
			if (m_values.isEmpty())
			{
				ask();
			}
			if (m_values.isEmpty())
			{
				throw new NoMoreElementException();
			}
			T value = m_values.remove();
			return value;
		}

		@Override
		public Picker<T> duplicate(boolean with_state)
		{
			throw new UnsupportedOperationException();
		}
	}
}
