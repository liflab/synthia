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
 */
public class StringPattern implements Picker<String>
{
	/*@ non_null @*/ protected Picker<?>[] m_pickers;
	
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
		super();
		m_pattern = pattern;
		m_pickers = parts;
	}
	
	@Override
	public String toString()
	{
		return m_pattern;
	}

	@Override
	public void reset() 
	{
		for (Picker<?> p : m_pickers)
		{
			p.reset();
		}
	}

	@Override
	public String pick()
	{
		String[] parts = new String[m_pickers.length];
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = m_pickers[i].pick().toString();
		}
		String out = m_pattern;
		for (int i = 0; i < parts.length; i++)
		{
			out = out.replaceAll("\\{\\$" + i + "\\}", parts[i]);
		}
		return out;
	}

	@Override
	public StringPattern duplicate(boolean with_state)
	{
		Picker<?>[] new_provs = new Picker<?>[m_pickers.length];
		for (int i = 0; i < m_pickers.length; i++)
		{
			new_provs[i] = m_pickers[i].duplicate(with_state);
		}
		return new StringPattern(m_pattern, new_provs);
	}
}
