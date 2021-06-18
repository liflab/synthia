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

import ca.uqac.lif.synthia.random.generators.Random;

/**
 * Picks a floating point number uniformly in the interval [0,1]
 */
public class RandomFloat extends RandomPicker<Float>
{
	/**
	 * Creates a new instance of the picker
	 */
	public RandomFloat()
	{
		super();
	}

	/**
	 * Private constructor used for the duplication of the picker.
	 * @param seed The initial seed of the random generator.
	 * @param random The random generator.
	 */
	private RandomFloat(int seed, Random random)
	{
		m_seed = seed;
		m_random = random;
	}

	/**
	 * Picks a random floating point uniformly in the interval [0,1]. Typically, this method is expected
	 * to return non-null objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random float.
	 */
	@Override
	public Float pick() 
	{
		return m_random.nextFloat();
	}


	/**
	 * Creates a copy of the RandomFloat picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomFloat picker
	 */
	@Override
	public RandomFloat duplicate(boolean with_state)
	{
		RandomFloat copy = new RandomFloat(m_seed, m_random.Duplicate());

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}
}