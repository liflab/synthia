/*
    Synthia, a data structure generator
    Copyright (C) 2019-2020 Laboratoire d'informatique formelle
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

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that merges the result of other pickers into a list.
 */
public class ListPicker extends CompositePicker<List<Object>>
{
	protected Picker<Integer> m_sizePicker;


	/**
	 * Default constructor using a constant list size.
	 * @param pickers The array of pickers used to generate the values
	 */
	public ListPicker(Picker<?> ... pickers)
	{
		super(pickers);
		m_sizePicker = new Constant<>(pickers.length);
	}

	/**
	 * Constructor who takes a {@link ca.uqac.lif.synthia.Picker<Integer>} to determine the size of
	 * the list returned by the {@link #pick()} method.
	 *
	 * @param size_picker A picker who picks the size of the list returned by
	 *                    the {@link #pick()} method.
	 * @param pickers A list of {@link ca.uqac.lif.synthia.Picker} used to produce lists of values.
	 */
	public ListPicker(Picker<Integer> size_picker, Picker<?>... pickers)
	{
		super(pickers);
		m_sizePicker = size_picker;
	}

	/**
	 * Returns a new list picker. This version will return an instance who returns lists of constant
	 * size with {@link #pick()} method.
	 *
	 * @param pickers The pickers used to generate the values
	 * @return A new instance of {@link ListPicker}.
	 */
	@Override
	public ListPicker newPicker(Picker<?> ... pickers)
	{
		return new ListPicker(pickers);
	}

	/**
	 * Returns a new list picker. This version will return an instance who returns lists of variable
	 * size with {@link #pick()} method. The size of the lists returned by the {@link #pick()} method
	 * is determined by a {@link ca.uqac.lif.synthia.Picker<Integer>}.
	 *
	 * @param size_picker A picker who picks the size of the list returned by
	 *                    the {@link #pick()} method.
	 * @param pickers A list of {@link ca.uqac.lif.synthia.Picker} used to produce lists of values.
	 * @return A new instance of {@link ListPicker}.
	 */
	public ListPicker newPicker(Picker<Integer> size_picker, Picker<?>... pickers)
	{
		return new ListPicker(size_picker, pickers);
	}

	/**
	 * Return a list of values
	 * @param values The values to return
	 * @return The values
	 */
	@Override
	public List<Object> getOutput(Object ... values)
	{
		List<Object> list = new ArrayList<>(values.length);
		Collections.addAll(list, values);
		return list;
	}

	@Override
	public List<Object> pick ()
	{
		List<Object> picked_elements = new ArrayList<>();
		int size = m_sizePicker.pick();
		int index =0;

		for (int i = 0; i < size; i++)
		{
			picked_elements.add(m_pickers[index].pick());
			index++;

			// reset index if size >= m_pickers.length
			if (index == m_pickers.length)
			{
				index = 0;
			}
		}

		return picked_elements;
	}

	@Override
	public void reset()
	{
		super.reset(); //reset m_pickers
		m_sizePicker.reset();
	}

	@Override
	public ListPicker duplicate(boolean with_state)
	{
		return newPicker(m_sizePicker.duplicate(with_state), super.duplicateM_pickers(with_state));
	}
}