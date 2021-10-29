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
package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.relative.NothingPicker;

/**
 * Just like {@link RandomPrefix} but for suffixes.
 * @author Marc-Antoine Plourde
 * @ingroup API
 */
public class RandomSuffix extends RandomPrefix
{

	public RandomSuffix(String string)
	{
		super(string);
	}

	private RandomSuffix(String string, RandomInteger prefix_size)
	{
		super(string, prefix_size);
	}

	@Override
	public Shrinkable<String> shrink(String element)
	{
		if(element.isEmpty())
		{
			return new NothingPicker<>();
		}
		return new RandomSuffix(element, m_prefixSize.duplicate(true));
	}

	@Override
	public String pick()
	{
		return m_string.substring(m_prefixSize.pick(), m_string.length());
	}

	@Override
	public RandomSuffix duplicate(boolean with_state)
	{
		return new RandomSuffix(m_string, m_prefixSize.duplicate(with_state));
	}

}
