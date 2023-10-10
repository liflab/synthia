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
package ca.uqac.lif.synthia.enumerative;

import ca.uqac.lif.synthia.Bounded;

/**
 * Successively enumerates the values of multiple {@link Bounded} pickers.
 * @param <T> The type of the elements produced
 * @ingroup API
 */
public class Merge<T> implements Bounded<T>
{
	/**
	 * The array of pickers to enumerate values from.
	 */
	/*@ non_null @*/ protected final Bounded<T>[] m_pickers;
	
	/**
	 * The index in the array of the picker that is currently being enumerated.
	 */
	protected int m_currentIndex;
	
	/**
	 * Creates a new merge instance.
	 * @param pickers The pickers to enumerate values from
	 */
	@SuppressWarnings("unchecked")
	public Merge(Bounded<T> ... pickers)
	{
		super();
		m_pickers = pickers;
		m_currentIndex = 0;
	}

	@Override
	public void reset()
	{
		for (Bounded<T> p : m_pickers)
		{
			p.reset();
		}
		m_currentIndex = 0;
		
	}

	@Override
	public T pick()
	{
		if (m_pickers[m_currentIndex].isDone())
		{
			m_currentIndex++;
		}
		return m_pickers[m_currentIndex].pick();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Merge<T> duplicate(boolean with_state)
	{
		Bounded<T>[] bounded = new Bounded[m_pickers.length];
		for (int i = 0; i < bounded.length; i++)
		{
			bounded[i] = (Bounded<T>) m_pickers[i].duplicate(with_state);
		}
		Merge<T> new_merge = new Merge<T>(bounded);
		if (with_state)
		{
			new_merge.m_currentIndex = m_currentIndex;
		}
		return new_merge;
	}

	@Override
	public boolean isDone()
	{
		while (m_currentIndex < m_pickers.length)
		{
			if (!m_pickers[m_currentIndex].isDone())
			{
				return false;
			}
			m_currentIndex++;
		}
		return true;
	}
}
