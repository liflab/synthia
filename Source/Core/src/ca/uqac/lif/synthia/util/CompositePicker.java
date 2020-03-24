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

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that merges the result of other pickers into a
 * composite data structure. Descendants of this class use a
 * different data structure.
 */
public abstract class CompositePicker<T> implements Picker<T>
{
	/**
	 * The pickers used to generate the values
	 */
	/*@ non_null @*/ protected Picker<?>[] m_pickers;
	
	/**
	 * Creates a new composite picker 
	 * @param pickers The pickers used to generate the values
	 */
	public CompositePicker(/*@ non_null @*/ Picker<?> ... pickers)
	{
		super();
		m_pickers = pickers;
	}
	
	@Override
	public Picker<T> duplicate(boolean with_state)
	{
		Picker<?>[] duplicates = new Picker<?>[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			duplicates[i] = m_pickers[i].duplicate(with_state);
		}
		return newPicker(duplicates);
	}
	
	@Override
	public T pick()
	{
		Object[] out = new Object[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			out[i] = m_pickers[i].pick();
		}
		return getOutput(out);
	}
	
	@Override
	public void reset()
	{
		for (int i = 0; i < m_pickers.length; i++)
		{
			m_pickers[i].reset();
		}
	}
	
	/**
	 * Creates a duplicate of the current picker
	 * @param pickers The internal pickers, already duplicated
	 * @return A new instance of the picker
	 */
	protected abstract CompositePicker<T> newPicker(Picker<?> ... pickers);
	
	/**
	 * Creates the output composite object from the internal
	 * values that have been picked
	 * @param objects The internal values
	 * @return The composite object
	 */
	protected abstract T getOutput(Object ... objects);
}
