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
 * Utility picker that converts an input into an integer.
 * @author Sylvain Hallé
 * @ingroup API
 */
public class AsInt implements Picker<Integer>
{
	/**
	 * The picker from which to take the input objects.
	 */
	protected Picker<?> m_picker;
	
	/**
	 * Creates a new instance of the picker.
	 * @param picker The picker from which to take the input objects
	 */
	public AsInt(Picker<?> picker)
	{
		super();
		m_picker = picker;
	}

	@Override
	public Integer pick()
	{
		Object o = m_picker.pick();
		if (o == null)
		{
			return 0;
		}
		if (o instanceof Number)
		{
			return ((Number) o).intValue();
		}
		return 0;
	}

	@Override
	public AsInt duplicate(boolean with_state)
	{
		return new AsInt(m_picker.duplicate(with_state));
	}

	@Override
	public void reset()
	{
		m_picker.reset();
	}
}
