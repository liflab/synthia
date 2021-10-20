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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.Picker;

/**
 * Mutator that receives a list and selects two elements to be swapped.
 *
 * @param <T> The type of elements in the list
 */
public class Swap<T> extends Mutator<List<T>>
{
	/**
	 * A picker used to select the position of the first element.
	 */
	/*@ non_null @*/ protected Picker<Float> m_position1;
	
	/**
	 * A picker used to select the position of the second element.
	 */
	/*@ non_null @*/ protected Picker<Float> m_position2;
	
	/**
	 * Creates a new instance of swap.
	 * @param picker The underlying picker producing the lists to transform
	 * @param position1 A picker used to select the position of the first element
	 * @param position2 A picker used to select the position of the second element
	 */
	public Swap(Picker<List<T>> picker, Picker<Float> position1, Picker<Float> position2)
	{
		super(picker);
		m_position1 = position1;
		m_position2 = position2;
	}

	@Override
	public List<T> pick()
	{
		List<T> original = m_picker.pick();
		int len = original.size();
		int position1 = (int) Math.floor(m_position1.pick() * len);
		int position2 = (int) Math.floor(m_position2.pick() * len);
		List<T> new_list = new ArrayList<T>(len);
		new_list.addAll(original);
		Collections.swap(new_list, position1, position2);
		return new_list;
	}

	@Override
	public Swap<T> duplicate(boolean with_state)
	{
		return new Swap<T>(m_picker.duplicate(with_state), m_position1.duplicate(with_state), m_position2.duplicate(with_state));
	}
}
