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

import ca.uqac.lif.synthia.Seedable;
import org.apache.commons.lang3.RandomStringUtils;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a random character string.
 */
public class RandomString implements Picker<String>, Seedable
{
	/**
	 * A picker used to determine the string's length
	 */
	protected Picker<Integer> m_lengthPicker;

	protected RandomInteger m_charValuePicker;


	/**
	 * Creates a new RandomString picker
	 * @param length A picker used to determine the string's length
	 */
	public RandomString(Picker<Integer> length)
	{
		super();
		m_lengthPicker = length;
		m_charValuePicker = new RandomInteger(48,122); //ASCII numeric value range
	}

	/**
	 * Private constructor used to duplicate the picker.
	 * @param length The picker used to pick the lenght of the random string.
	 * @param char_value_picker The picker used to generate random strings.
	 */
	private RandomString(Picker<Integer> length, RandomInteger char_value_picker)
	{
		super();
		m_lengthPicker = length;
		m_charValuePicker = char_value_picker;
	}
	
	/**
	 * Creates a new RandomString picker
	 * @param length The length of each generated string
	 */
	public RandomString(int length)
	{
		super();
		m_lengthPicker = new Constant<Integer>(length);
		m_charValuePicker = new RandomInteger(48,122); //ASCII numeric value range
	}

	/**
	 * Puts the RandomString picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset() 
	{
		m_lengthPicker.reset();
		m_charValuePicker.reset();
	}


	/**
	 * Picks a random string. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random string.
	 */
	@Override
	public String pick() 
	{
		int len = m_lengthPicker.pick();
		List<Integer> char_code_values = new ArrayList<Integer>();
		int next_char_value;
		for (int i = 0; i < len; i++)
		{
				next_char_value = m_charValuePicker.pick();

				while (!((next_char_value >= 48 && next_char_value <= 57) ||
						(next_char_value >= 65 && next_char_value <= 90) ||
						(next_char_value >= 97 && next_char_value <= 122)))
				{
					next_char_value = m_charValuePicker.pick();
				}

				char_code_values.add(next_char_value);
		}

		return toString(char_code_values);
	}

	/**
	 * Private method used to convert a list of integers representing each ascii code of
	 * the random string into a string.
	 * @param char_code_values The list containing each ascii code of the string.
	 * @return A string.
	 */
	private String toString(List<Integer> char_code_values)
	{
		StringBuilder str= new StringBuilder();

		for (Integer char_code_value : char_code_values)
		{

			str.append((char) char_code_value.intValue());
		}

		return str.toString();
	}

	/**
	 * Creates a copy of the RandomString picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomString picker
	 */
	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomString(m_lengthPicker.duplicate(with_state),
				m_charValuePicker.duplicate(with_state));
	}

	@Override
	public void setSeed(int seed)
	{
		m_charValuePicker.setSeed(seed);
	}
}
