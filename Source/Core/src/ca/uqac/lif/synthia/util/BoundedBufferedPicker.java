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

import ca.uqac.lif.synthia.Bounded;

/**
 * A {@link BufferedPicker} that is also {@link Bounded}. A call to
 * {@link #isDone()} returns true if the queue is empty and the call to
 * {@link #fillQueue()} does not add any new elements to it.
 * @param <T> The type of objects to pick
 * @author Sylvain Hallé
 * @since 0.3.3
 */
public abstract class BoundedBufferedPicker<T> extends BufferedPicker<T> implements Bounded<T>
{
	@Override
	public boolean isDone()
	{
		if (!m_queue.isEmpty())
		{
			return false;
		}
		fillQueue();
		return m_queue.isEmpty();
	}
}
