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
package ca.uqac.lif.synthia.util;





import ca.uqac.lif.synthia.Picker;

/**
 * A picker that applies a transformation ("mutation") on the value produced by
 * another picker.
 * @param <T> The type of the objects produced by the picker
 * @ingroup API
 */
public abstract class Mutator<T> implements Picker<T>
{
	/**
	 * The underlying picker producing the values to transform.
	 */
	/*@ non_null @*/ protected Picker<? extends T> m_picker;
	
	/**
	 * Creates a new instance of mutator.
	 * @param picker The underlying picker producing the values to transform
	 */
	public Mutator(/*@ non_null @*/ Picker<? extends T> picker)
	{
		super();
		m_picker = picker;
	}
	
	/**
	 * Sets the picker producing the values to transform.
	 * @param picker The underlying picker producing the values to transform
	 * @return This picker
	 */
	/*@ non_null @*/ public Mutator<T> setPicker(/*@ non_null @*/ Picker<? extends T> picker)
	{
		m_picker = picker;
		return this;
	}
	
	@Override
	public void reset()
	{
		m_picker.reset();
	}
	
	protected void copyInto(/*@ non_null @*/ Mutator<T> m, boolean with_state)
	{
		// Do nothing
	}
}
