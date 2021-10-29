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
package ca.uqac.lif.synthia.collection;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Mutator;

/**
 * Picker that selects an element of a list and applies the result of a mutator
 * on this element.
 *
 * @param <T> The type of the elements in the list
 * @ingroup API
 */
public class MutateElement<T> extends Mutator<List<T>>
{
	/**
	 * A picker used to select the position of the first element.
	 */
	protected Picker<Float> m_position;
	
	/**
	 * A mutator picker to mutate elements of the list.
	 */
	protected Picker<Mutator<? extends T>> m_mutator;
	
	/**
	 * Creates a new instance of the picker.
	 * @param picker The underlying picker producing the lists to transform
	 * @param position A picker used to select the position of the first element
	 * @param mutator A mutator picker to mutate elements of the list
	 */
	public MutateElement(Picker<? extends List<T>> picker, Picker<Float> position, Picker<Mutator<? extends T>> mutator)
	{
		super(picker);
		m_position = position;
		m_mutator = mutator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> pick()
	{
		List<T> list = m_picker.pick();
		int size = list.size();
		int position = (int) Math.floor(m_position.pick() * (double) size);
		T e = list.get(position);
		Mutator<T> m = (Mutator<T>) m_mutator.pick();
		m.setPicker(new Constant<T>(e));
		T e_m = m.pick();
		List<T> new_list = new ArrayList<T>(size);
		for (int i = 0; i < size; i++)
		{
			if (i == position)
			{
				new_list.add(e_m);
			}
			else
			{
				new_list.add(list.get(i));
			}
		}
		return new_list;
	}
	
	@Override
	public void reset()
	{
		m_position.reset();
		m_mutator.reset();
	}

	@Override
	public MutateElement<T> duplicate(boolean with_state)
	{
		return new MutateElement<T>(m_picker.duplicate(with_state), m_position.duplicate(with_state), m_mutator.duplicate(with_state));
	}
}
