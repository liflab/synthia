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
package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.collection.CompositePicker;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.relative.PickSmallerComparable;

/**
 * Generates a string according to a predefined pattern. The picker is
 * instantiated by giving it a string pattern; this pattern can contain
 * placeholders of the form <tt>{$n}</tt>; when called, the picker will
 * replace <tt>{$n}</tt> by the value returned by the <tt>n</tt>-th picker
 * passed to the constructor. For example:
 * <pre>
 * StringPattern pat = new StringPattern("{$0} is equal to {$1}",
 *   new RandomString(5), new RandomBoolean());
 * String s1 = pat.pick(); // "FhTuN is equal to false"
 * String s2 = pat.pick(); // "aGhRe is equal to true"
 * ...</pre>
 * @ingroup API
 */
public class StringPattern extends CompositePicker<String> implements Shrinkable<String>
{	
	/**
	 * The string pattern
	 */
	/*@ non_null @*/ protected String m_pattern;

	/**
	 * Creates a new StringPattern picker
	 * @param pattern The string pattern
	 * @param parts The pickers used to give values to each placeholder
	 */
	public StringPattern(/*@ non_null @*/ String pattern, /*@ non_null @*/ Picker<?> ... parts)
	{
		super(parts);
		m_pattern = pattern;
	}


	/**
	 * Returns the pattern into a string
	 * @return The string containing the pattern.
	 */
	@Override
	public String toString()
	{
		return m_pattern;
	}

	@Override
	public String getOutput(Object ... parts)
	{
		String out = m_pattern;
		for (int i = 0; i < parts.length; i++)
		{
			out = out.replaceAll("\\{\\$" + i + "\\}", parts[i].toString());
		}
		return out;
	}

	/**
	 * Returns a new string pattern picker.
	 * @param pickers The internal pickers, already duplicated
	 * @return The new string pattern picker.
	 */
	@Override
	public StringPattern newPicker(Picker<?> ... pickers)
	{
		return new StringPattern(m_pattern, pickers);
	}

	@Override
	public Shrinkable<String> shrink(String o, Picker<Float> decision, float magnitude)
	{
		return new PickSmallerComparable<String>(this, o);
	}

	@Override
	public Shrinkable<String> shrink(String o)
	{
		return shrink(o, RandomFloat.instance, 1);
	}
}
