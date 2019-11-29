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
package ca.uqac.lif.synthia.random;

import org.apache.commons.lang3.RandomStringUtils;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

/**
 * Generates a random character string.
 */
public class RandomString implements Picker<String>
{
	/**
	 * A picker used to determine the string's length
	 */
	protected Picker<Integer> m_lengthPicker;
	
	/**
	 * Creates a new RandomString picker
	 * @param length A picker used to determine the string's length
	 */
	public RandomString(Picker<Integer> length)
	{
		super();
		m_lengthPicker = length;
	}
	
	/**
	 * Creates a new RandomString picker
	 * @param length The length of each generated string
	 */
	public RandomString(int length)
	{
		super();
		m_lengthPicker = new Constant<Integer>(length);
	}
	
	@Override
	public void reset() 
	{
		// Nothing to do
	}

	@Override
	public String pick() 
	{
		int len = m_lengthPicker.pick();
		return RandomStringUtils.randomAlphanumeric(len, len);
	}

	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomString(m_lengthPicker);
	}
}
