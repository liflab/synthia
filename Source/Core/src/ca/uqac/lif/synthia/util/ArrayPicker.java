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

import ca.uqac.lif.synthia.CannotShrinkException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.collection.CompositePicker;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * Picker that merges the result of other pickers into an array.
 * @ingroup API
 */
public class ArrayPicker extends CompositePicker<Object[]> implements Shrinkable<Object[]>
{
	/**
	 * Creates a new array picker 
	 * @param pickers The pickers used to generate the values
	 */
	public ArrayPicker(Picker<?> ... pickers)
	{
		super(pickers);
	}

	/**
	 * Returns a new array picker
	 * @param pickers The pickers used to generate the values
	 * @return The new array picker
	 */
	@Override
	public ArrayPicker newPicker(Picker<?> ... pickers)
	{
		return new ArrayPicker(pickers);
	}


	/**
	 * Return an array of values
	 * @param values The values to return
	 * @return The values
	 */
	@Override
	public Object[] getOutput(Object ... values)
	{
		return values;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ArrayPicker shrink(Object[] o, Picker<Float> decision, float magnitude)
	{
		Picker[] shrunk_pickers = new Picker[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			if (!(m_pickers[i] instanceof Shrinkable))
			{
				throw new CannotShrinkException(m_pickers[i]);
			}
			shrunk_pickers[i] = ((Shrinkable<Object>) m_pickers[i]).shrink(o[i], decision, 1);
		}
		return new ArrayPicker(shrunk_pickers);
	}

	@Override
	public ArrayPicker shrink(Object[] o)
	{
		return shrink(o, RandomFloat.instance, 1);
	}
}