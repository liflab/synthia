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
package ca.uqac.lif.synthia.vector;

import ca.uqac.lif.synthia.Picker;

/**
 * Interface for pickers that generate arrays of floating-point
 * numbers.
 * 
 * @ingroup API
 */
public interface VectorPicker extends Picker<float[]>
{
	/**
	 * Returns the dimension of the picker, i.e. the number of elements
	 * in the vectors it generates.
	 * @return The dimension
	 */
	public int getDimension();
	
	@Override
	public VectorPicker duplicate(boolean with_state);
}
