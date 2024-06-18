/*
    Synthia, a data structure generator
    Copyright (C) 2019-2024 Laboratoire d'informatique formelle
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
import java.util.Queue;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

/**
 * Picker that fetches objects from a queue that is periodically filled by a
 * call to its underlying method {@link #fillQueue()}. The picker depletes the
 * queue before calling {@link #fillQueue()} again.
 * @param <T> The type of objects to pick
 * @author Sylvain Hallé
 * @since 0.3.3
 */
public abstract class BufferedPicker<T> implements Picker<T>
{
	/**
	 * A queue holding events already generated by the underlying picker.
	 */
	/*@ non_null @*/ protected final Queue<T> m_queue;
	
	/**
	 * Creates a new buffered picker.
	 * @param picker The underlying picker producing objects
	 */
	public BufferedPicker()
	{
		super();
		m_queue = new ArrayDeque<T>();
	}
	
	@Override
	public void reset()
	{
		m_queue.clear();
	}

	@Override
	public T pick()
	{
		if (!m_queue.isEmpty())
		{
			return m_queue.remove();
		}
		fillQueue();
		if (m_queue.isEmpty())
		{
			throw new NoMoreElementException();
		}
		return m_queue.remove();
	}
	
	protected void copyInto(BufferedPicker<T> bp, boolean with_state)
	{
		if (with_state)
		{
			bp.m_queue.addAll(m_queue);
		}
	}
	
	/**
	 * Fills the queue with new elements to enumerate.
	 */
	protected abstract void fillQueue();
}