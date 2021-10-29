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

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.PickerException;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * A variant of {@link PickIf} that selects an element if it is smaller than
 * a reference object. In order for the "smaller" condition to be evaluated,
 * the objects that are manipulated must implement the {@link Comparable}
 * interface.
 *   
 * @author Sylvain Hallé
 *
 * @param <T> The type of objects manipulated by this picker
 * @ingroup API
 */
public class PickSmallerComparable<T> extends PickIf<T> implements Shrinkable<T>
{
	/**
	 * The reference object that candidate values are compared to.
	 */
	protected Comparable<T> m_reference;
	
	/**
	 * Creates a new instance of the picker.
	 * @param source The source of objects
	 * @param o The reference object that candidate values are compared to
	 */
	@SuppressWarnings("unchecked")
	public PickSmallerComparable(Picker<? extends T> source, T o)
	{
		super(source);
		if (!(o instanceof Comparable))
		{
			throw new PickerException("Expected a comparable object");
		}
		m_reference = (Comparable<T>) o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickSmallerComparable<T> duplicate(boolean with_state)
	{
		PickSmallerComparable<T> ps = new PickSmallerComparable<T>(m_picker.duplicate(with_state), (T) m_reference);
		super.copyInto(this, with_state);
		if (with_state)
		{
			ps.m_maxIteration = m_maxIteration;
		}
		return ps;
	}

	@Override
	public PickSmallerComparable<T> shrink(T o, Picker<Float> decision)
	{
		PickSmallerComparable<T> ps = new PickSmallerComparable<T>(m_picker, (T) o);
		ps.m_maxIteration = m_maxIteration;
		return ps;
	}

	@Override
	protected boolean select(T element)
	{
		return m_reference.compareTo(element) > 0;
	}

	@Override
	public Shrinkable<T> shrink(T o)
	{
		return shrink(o, RandomFloat.instance);
	}
}
