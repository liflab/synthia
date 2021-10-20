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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that merges the result of other pickers into a set.
 */
public class SetPicker extends CompositePicker<Set<Object>>
{
	/**
	 * Creates a new set picker 
	 * @param pickers The pickers used to generate the values
	 */
	public SetPicker(Picker<?> ... pickers)
	{
		super(pickers);
	}

	/**
	 * Returns a new set picker
	 * @param pickers The pickers used to generate the values
	 * @return The new set picker
	 */
	@Override
	public SetPicker newPicker(Picker<?> ... pickers)
	{
		return new SetPicker(pickers);
	}



	@Override
	public Set<Object> getOutput(Object ... values)
	{
		Set<Object> set = new HashSet<>(values.length);
		Collections.addAll(set, values);
		return set;
	}	
}