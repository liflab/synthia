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
package ca.uqac.lif.synthia.relative;

import java.util.Comparator;

import ca.uqac.lif.synthia.Picker;

public class PickSmallerComparator<T> extends PickIf<T>
{
	/**
	 * A comparator for objects of type T.
	 */
	protected Comparator<T> m_comparator;
	
	/**
	 * The reference object that candidate values are compared to.
	 */
	protected T m_reference;
	
	public PickSmallerComparator(Picker<? extends T> source, T o, Comparator<T> c)
	{
		super(source);
		m_reference = o;
		m_comparator = c;
	}

	@Override
	public PickSmallerComparator<T> duplicate(boolean with_state)
	{
		PickSmallerComparator<T> ps = new PickSmallerComparator<T>(m_picker.duplicate(with_state), m_reference, m_comparator);
		if (with_state)
		{
			ps.m_maxIteration = m_maxIteration;
		}
		return ps;
	}
	public PickSmallerComparator<T> getSmaller(T o, Comparator<T> c)
	{
		PickSmallerComparator<T> ps = new PickSmallerComparator<T>(m_picker, o, c);
		ps.m_maxIteration = m_maxIteration;
		return ps;
	}

	@Override
	protected boolean select(T element)
	{
		return m_comparator.compare(element, m_reference) < 0;
	}
}
