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

/**
 * Generates pseudo-random floating-point numbers in the interval [0,1],
 * but according to a probability distribution that is not uniform.
 * Concretely, a value is first picked uniformly, and is then multiplied
 * by a factor &beta; called the bias. The resulting value is then
 * replaced by 0 or 1 if it lies outside the interval. Setting
 * &beta; &lt; 1 results in a picker that favors values closer to 0, while
 * setting &beta; &gt; 1 results in a picker that favors values closer to 1.
 * 
 * @author Sylvain Hallé
 *
 */
public class BiasedRandomFloat extends RandomFloat
{
	/**
	 * The bias parameter &beta;.
	 */
	protected float m_beta;
	
	/**
	 * Creates a new instance of the biased random float picker.
	 * @param beta The bias parameter &beta;
	 */
	public BiasedRandomFloat(float beta)
	{
		super();
		m_beta = beta;
	}
	
	@Override
	public Float pick()
	{
		float f = super.pick();
		float biased_f = f * m_beta;
		if (biased_f < 0)
		{
			return 0f;
		}
		if (biased_f > 1)
		{
			return 1f - 0.000001f;
		}
		return biased_f;
	}
	
	@Override
	public BiasedRandomFloat duplicate(boolean with_state)
	{
		return new BiasedRandomFloat(m_beta);
	}
}
