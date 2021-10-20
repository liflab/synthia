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

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.relative.PickSmallerComparable;
import ca.uqac.lif.synthia.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates a random characters string.
 */
public class RandomString implements Shrinkable<String>, Seedable
{
	/**
	 * A picker used to determine the string's length
	 */
	protected Picker<Integer> m_lengthPicker;

	protected RandomInteger m_charIndexPicker;

	protected char[] m_chars;

	/**
	 * Creates a new RandomString picker with a default alphanumeric values array
	 *
	 * @param length A picker used to determine the string's length
	 */
	public RandomString(Picker<Integer> length)
	{
		super();
		defaultCharArrayInitialize();
		m_lengthPicker = length;
		m_charIndexPicker = new RandomInteger(0, m_chars.length);
	}

	/**
	 * Creates a new RandomString picker with a default alphanumeric values array
	 *
	 * @param length     A picker used to determine the string's length
	 * @param char_array An array containing the characters allowed in the random string.
	 */
	public RandomString(Picker<Integer> length, char[] char_array)
	{
		super();
		m_chars = char_array;
		m_lengthPicker = length;
		m_charIndexPicker = new RandomInteger(0, m_chars.length);
	}

	/**
	 * Private constructor used to duplicate the picker.
	 *
	 * @param length            The picker used to pick the lenght of the random string.
	 * @param char_index_picker The picker used to generate random strings.
	 * @param char_array        An array containing the characters allowed in the random string.
	 */
	private RandomString(Picker<Integer> length, RandomInteger char_index_picker, char[] char_array)
	{
		super();
		m_lengthPicker = length;
		m_charIndexPicker = char_index_picker;
		m_chars = char_array;
	}

	/**
	 * Creates a new RandomString picker, with a default alphanumeric values array.
	 *
	 * @param length The max length of each generated string.
	 */
	public RandomString(int length)
	{
		super();
		defaultCharArrayInitialize();
		m_lengthPicker = new Constant<>(length);
		m_charIndexPicker = new RandomInteger(0, m_chars.length);
	}

	/**
	 * Creates a new RandomString picker, with a specified alphanumeric values array.
	 *
	 * @param length     The length of each generated string.
	 * @param char_array An array containing the characters allowed in the random string.
	 */
	public RandomString(int length, char[] char_array)
	{
		super();
		m_lengthPicker = new Constant<>(length);
		m_chars = char_array;
		m_charIndexPicker = new RandomInteger(0, m_chars.length);
	}

	/**
	 * A private method to initialize the default characters array
	 */
	private void defaultCharArrayInitialize()
	{
		m_chars = new char[62];
		int i;

		// 0 to 9
		for (i = 0; i < 10; i++)
		{
			m_chars[i] = (char) (i + 48);
		}

		// A to Z and a to z
		for (i = 0; i < 26; i++)
		{
			m_chars[i + 10] = (char) (i + 65);
			m_chars[i + 36] = (char) (i + 97);
		}
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
		m_charIndexPicker.reset();
	}

	/**
	 * Picks a random string. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 *
	 * @return The random string.
	 */
	@Override
	public String pick()
	{
		int len = m_lengthPicker.pick();
		List<Integer> char_index_list = new ArrayList<>();
		int char_index;
		for (int i = 0; i < len; i++)
		{
			char_index = m_charIndexPicker.pick();
			char_index_list.add(char_index);
		}

		return toString(char_index_list);
	}

	/**
	 * Private method used to convert a list of integers representing indexes in the m_chars
	 * array into a string.
	 *
	 * @param char_index_list The list containing the indexes in the m_chars array who will be used
	 *                        to create the string.
	 * @return A string.
	 */
	private String toString(List<Integer> char_index_list)
	{
		StringBuilder str = new StringBuilder();

		for (Integer char_code_value : char_index_list)
		{
			str.append(m_chars[char_code_value]);
		}

		return str.toString();
	}

	/**
	 * Creates a copy of the RandomString picker.
	 *
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 *                   the class' initial state (i.e. same thing as calling the picker's
	 *                   constructor). If set to <tt>true</tt>, the returned copy is put into the
	 *                   same internal state as the object it is copied from.
	 * @return The copy of the RandomString picker
	 */
	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RandomString(m_lengthPicker.duplicate(with_state),
				m_charIndexPicker.duplicate(with_state), m_chars);
	}

	@Override
	public RandomString setSeed(int seed)
	{
		m_charIndexPicker.setSeed(seed);
		return this;
	}
	
	@Override
	public Shrinkable<String> shrink(String o)
	{
		return new PickSmallerComparable<String>(this, o);
	}

	//can only be used if m_lenghtPicker is a RandomInteger or RandomIndex
	public void setInterval(int min, int max)
	{

		if ( m_lengthPicker.getClass().getSimpleName().equals("RandomInteger"))
		{
			RandomInteger random_integer_copy = (RandomInteger) m_lengthPicker.duplicate(true);
			random_integer_copy.setInterval(min, max);
			m_lengthPicker = random_integer_copy;
		}
		else if (m_lengthPicker.getClass().getSimpleName().equals("RandomIndex"))
		{
			RandomInteger random_integer_copy = (RandomInteger) m_lengthPicker.duplicate(true);
			random_integer_copy.setInterval(min, max);
			m_lengthPicker = random_integer_copy;
		}
	}

}
