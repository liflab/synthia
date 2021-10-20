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
package ca.uqac.lif.synthia;

import ca.uqac.lif.petitpoucet.Part;

/**
 * A {@link Part} pointing to the n-th output produced by a picker since its
 * last call to {@link Picker#reset() reset()}.
 */
public class NthSuccessiveOutput implements Part
{
	/**
	 * The index in the sequence of the output since the last reset.
	 */
	public int m_index;
	
	/**
	 * Creates a new instance of the part.
	 * @param index The index in the sequence of the output since the last reset
	 */
	public NthSuccessiveOutput(int index)
	{
		super();
		m_index = index;
	}
	
	/**
	 * Returns the index in the sequence of the output since the last reset.
	 * @return The index
	 */
	public int getIndex()
	{
		return m_index;
	}
	
	@Override
	public String toString()
	{
		return "Output value " + m_index;
	}
	
	@Override
	public boolean appliesTo(Object o)
	{
		return o instanceof Picker;
	}

	@Override
	public Part head()
	{
		return this;
	}

	@Override
	public Part tail()
	{
		return null;
	}
}
