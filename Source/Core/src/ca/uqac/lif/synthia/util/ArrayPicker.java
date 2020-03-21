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
 * Picker that merges the result of other pickers into an array.
 */
public class ArrayPicker implements Picker<Object[]>
{
	protected Picker<?>[] m_pickers;

	public ArrayPicker(Picker<?> ... pickers)
	{
		super();
		m_pickers = pickers;
	}

	@Override
	public Picker<Object[]> duplicate(boolean with_state)
	{
		Picker<?>[] duplicates = new Picker<?>[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			duplicates[i] = m_pickers[i].duplicate(with_state);
		}
		return new ArrayPicker(duplicates);
	}

	@Override
	public Object[] pick()
	{
		Object[] out = new Object[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			out[i] = m_pickers[i].pick();
		}
		return out;
	}

	@Override
	public void reset()
	{
		for (int i = 0; i < m_pickers.length; i++)
		{
			m_pickers[i].reset();
		}
	}
}