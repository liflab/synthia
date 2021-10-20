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
package ca.uqac.lif.synthia.vector;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;

//liste une vecteur avec un certain angle suivant une sphère de n dimensions
/**
 * Generates <i>n</i>-dimensional vectors with a given modulus. This
 * picker does so by taking a positive value <i>r</i> and <i>n</i>-1 pickers
 * providing angles &theta;<sub>1</sub>, &hellip; &theta;<sub><i>n</i>-1</sub>
 * (in radians). These values are taken as the <i>n</i>-dimensional polar
 * coordinates of a point, which is then turned into an <i>n</i>-dimensional
 * vector in Euclidean coordinates, as is defined in this paper:
 * <blockquote>
 * A. Muleshkov, T. Nguyen. (2013). Easy proof of the Jacobian for the n-dimensional
 * polar coordinates. (<a href="http://muleshko.faculty.unlv.edu/am_papers_pdf/n_d_polarcoordinates_note1.pdf">Paper</a>)
 * </blockquote>
 * The end result is that this picker generates vectors corresponding
 * to points that lie on the surface of an <i>n</i>-dimensional
 * hypersphere of some radius <i>r</i>.
 *
 */
public class HyperspherePicker implements VectorPicker
{
	/**
	 * The pickers for the radius and each polar angle
	 */
	protected Picker<? extends Number>[] m_dimensions;
	
	/**
	 * Creates a new hypersphere picker.
	 * @param dimensions The pickers used to generate values for
	 * each of the dimensions of the vector. The first corresponds
	 * to the radius, and the others to each polar angle.
	 */
	@SuppressWarnings("unchecked")
	public HyperspherePicker(Picker<? extends Number> ... dimensions)
	{
		super();
		m_dimensions = dimensions;
	}
	
	/**
	 * Creates a new hypershpere picker.
	 * @param modulus The modulus of the vectors to generate
	 * @param dimensions The pickers used to generate values for
	 * each polar angle.
	 */
	@SuppressWarnings("unchecked")
	public HyperspherePicker(float modulus, Picker<? extends Number> ... dimensions)
	{
		super();
		m_dimensions = new Picker[dimensions.length + 1];
		m_dimensions[0] = new Constant<Number>(modulus);
		for (int i = 0; i < dimensions.length; i++)
		{
			m_dimensions[i + 1] = dimensions[i];
		}

	}


	/**
	 * Puts the hypersphere picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset()
	{
		for (Picker<?> p : m_dimensions)
		{
			p.reset();
		}
	}

	@Override
	public float[] pick()
	{
		float r = m_dimensions[0].pick().floatValue();
		int n = m_dimensions.length;
		float[] v = new float[n];
		float sin_prod = 1;
		float last_theta = m_dimensions[n - 1].pick().floatValue();
		v[n - 1] = r * (float) Math.cos(last_theta);
		for (int i = n - 2; i > 1; i--)
		{
			sin_prod *= Math.sin(last_theta);
			last_theta = m_dimensions[i].pick().floatValue();
			v[i] = r * (float) Math.cos(last_theta);
		}
		last_theta = m_dimensions[1].pick().floatValue();
		v[0] = r * (float) Math.sin(last_theta) * sin_prod;
		return v;
	}

	/**
	 * Creates a copy of the hypersphere picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the hypersphere picker
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HyperspherePicker duplicate(boolean with_state)
	{
		Picker<? extends Number>[] dimensions = new Picker[m_dimensions.length];
		for (int i = 0; i < m_dimensions.length; i++)
		{
			dimensions[i] = m_dimensions[i].duplicate(with_state);
		}
		return new HyperspherePicker(dimensions);
	}
	
	@Override
	public int getDimension()
	{
		return m_dimensions.length;
	}
}