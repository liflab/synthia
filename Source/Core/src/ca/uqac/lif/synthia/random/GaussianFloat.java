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

/**
 * Produces a {@link float} picked from a Gaussian probability distribution.
 * @author Sylvain Hallé
 * @ingroup API
 */
public class GaussianFloat extends RandomPicker<Float>
{
	@Override
	public Float pick() 
	{
		return ((Double) m_random.nextGaussian()).floatValue();
	}

	@Override
	public Picker<Float> duplicate(boolean with_state) 
	{
		GaussianFloat gf = new GaussianFloat();
		gf.m_seed = m_seed;
		gf.m_random = this.m_random.Duplicate();

		if (!with_state)
		{
			gf.reset();
		}
		return gf;
	}

}
