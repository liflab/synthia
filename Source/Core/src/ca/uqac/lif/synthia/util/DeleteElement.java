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
import java.util.List;

import ca.uqac.lif.synthia.Mutator;
import ca.uqac.lif.synthia.Picker;

/**
 * Mutator that receives a list and selects an element to delete from it.
 *
 * @param <T> The type of elements in the list
 */
public class DeleteElement<T> extends Mutator<List<T>>
{
	/**
	 * A picker used to select the position of the element.
	 */
	/*@ non_null @*/ protected Picker<Float> m_position;
	
	/**
	 * Creates a new instance of the picker.
	 * @param picker The underlying picker producing the lists to transform
	 * @param position A picker used to select the position of the first element
	 */
	public DeleteElement(Picker<? extends List<T>> picker, Picker<Float> position)
	{
		super(picker);
		m_position = position;
	}

	@Override
	public List<T> pick()
	{
		List<T> original = m_picker.pick();
		int len = original.size();
		int position1 = (int) Math.floor(m_position.pick() * len);
		List<T> new_list = new ArrayList<T>(len);
		new_list.addAll(original);
		new_list.remove(position1);
		return new_list;
	}

	@Override
	public DeleteElement<T> duplicate(boolean with_state)
	{
		return new DeleteElement<T>(m_picker.duplicate(with_state), m_position.duplicate(with_state));
	}
}
