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
 * Generates a vector by independently picking a value for each of
 * its coordinates. If each of the pickers define a possible range of
 * values, this picker amounts to generating points inside a
 * multi-dimensional "prism" --hence its name.
 */
public class PrismPicker implements VectorPicker
{
	/**
	 * The pickers for each dimension of the vector
	 */
	protected Picker<Float>[] m_dimensions;
	
	/**
	 * Creates a new prism picker.
	 * @param dimensions The pickers used to generate values for
	 * each of the dimensions of the vector. If given <i>n</i>
	 * arguments, the resulting picker will produce <i>n</i>-dimensional
	 * vectors.
	 */
	@SuppressWarnings("unchecked")
	public PrismPicker(Picker<Float> ... dimensions)
	{
		super();
		m_dimensions = dimensions;
	}
	
	@Override
	public void reset()
	{
		for (Picker<Float> p : m_dimensions)
		{
			p.reset();
		}
	}

	@Override
	public float[] pick()
	{
		float[] v = new float[m_dimensions.length];
		for (int i = 0; i < m_dimensions.length; i++)
		{
			v[i] = m_dimensions[i].pick();
		}
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PrismPicker duplicate(boolean with_state)
	{
		Picker<Float>[] dimensions = new Picker[m_dimensions.length];
		for (int i = 0; i < m_dimensions.length; i++)
		{
			dimensions[i] = m_dimensions[i].duplicate(with_state);
		}
		return new PrismPicker(dimensions);
	}
	
	@Override
	public int getDimension()
	{
		return m_dimensions.length;
	}
}