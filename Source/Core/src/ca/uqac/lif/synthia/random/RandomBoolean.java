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

/**
 * Picks a Boolean value. This class actually implements a Bernoulli
 * trial, with the possibility of setting the probability of success
 * <i>p</i>.
 */
public class RandomBoolean extends RandomPicker<Boolean>
{
	/**
	 * The probability of picking <tt>true</tt>
	 */
	protected float m_trueProbability;
	
	/**
	 * Creates a new instance of the picker
	 * @param true_probability The probability of picking <tt>true</tt>;
	 * must be between 0 and 1.
	 */
	public RandomBoolean(/*@ non_null @*/ Number true_probability)
	{
		super();
		m_trueProbability = true_probability.floatValue();
	}
	
	/**
	 * Creates a new instance of the picker, with a 50-50 chance of
	 * producing <tt>true</tt> or <tt>false</tt>
	 */
	public RandomBoolean()
	{
		this(0.5);
	}
	
	@Override
	public Boolean pick()
	{
		return m_random.nextFloat() <= m_trueProbability;
	}

	@Override
	public RandomBoolean duplicate(boolean with_state) 
	{
		return new RandomBoolean(m_trueProbability);
	}
}
