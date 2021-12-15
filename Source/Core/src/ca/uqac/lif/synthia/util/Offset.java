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

import ca.uqac.lif.synthia.Picker;

/**
 * Takes the numerical value of a picker, and offsets it by an amount
 * determined by another picker.
 * @ingroup API
 */
public class Offset extends Mutator<Number>
{
	/**
	 * The picker determining the offset of each value.
	 */
	/*@ non_null @*/ protected Picker<? extends Number> m_offset;
	
	/**
	 * Creates a new offset instance.
	 * @param picker The underlying picker producing the values to transform
	 * @param offset The picker determining the offset of each output value
	 */
	public Offset(/*@ non_null @*/ Picker<? extends Number> picker, /*@ non_null @*/ Picker<? extends Number> offset)
	{
		super(picker);
		m_offset = offset;
	}

	@Override
	public Float pick()
	{
		float n = (Float) m_picker.pick();
		float o = (Float) m_offset.pick();
		return (Float) (n + o);
	}

	@Override
	public Offset duplicate(boolean with_state)
	{
		Offset o = new Offset(m_picker.duplicate(with_state), m_offset.duplicate(with_state));
		super.copyInto(o, with_state);
		return o;
	}
}
