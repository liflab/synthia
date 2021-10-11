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

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.generators.Random;
import ca.uqac.lif.synthia.relative.NothingPicker;

/**
 * Picks an integer uniformly in an interval
 */
public class RandomInteger extends RandomPicker<Integer> implements Shrinkable<Integer>
{
	/**
	 * The lower bound of the interval
	 */
	protected int m_min;
	
	/**
	 * The higher bound of the interval
	 */
	protected int m_max;

	/**
	 * Default constructor
	 */
	public RandomInteger()
	{
		super();
		setInterval(0,1);
	}


	/**
	 * Creates a new instance of the picker.
	 * @param min The lower bound of the interval
	 * @param max The higher bound of the interval
	 */
	public RandomInteger(int min, int max)
	{
		super();
		setInterval(min, max);
	}

	/**
	 * Protected constructor used to duplicate the picker.
	 * @param min The lower bound of the interval.
	 * @param max The higher bound of the interval.
	 * @param seed The initial seed of the random genarator.
	 * @param random The random generator.
	 */
	protected RandomInteger(int min, int max, int seed, Random random)
	{
		m_min = min;
		m_max = max;
		m_seed = seed;
		m_random = random;
	}
	
	/**
	 * Sets the interval in which integers are picked
	 * @param min The lower bound of the interval
	 * @param max The higher bound of the interval
	 */
	public RandomInteger setInterval(int min, int max)
	{
		m_min = min;
		m_max = max;
		return this;
	}




	/**
	 * Picks a random integer. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random integer
	 */
	@Override
	public Integer pick() 
	{
		return m_random.nextInt(m_max - m_min) + m_min;
	}


	/**
	 * Creates a copy of the RandomInteger picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomInteger picker
	 */
	@Override
	public RandomInteger duplicate(boolean with_state)
	{
		RandomInteger copy = new RandomInteger(m_min, m_max, m_seed, m_random.Duplicate());

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}
	
	@Override
	public Shrinkable<Integer> shrink(Integer element)
	{
		if(element <= m_min)
		{
			return new NothingPicker<Integer>();
		}
		else
		{
			return new RandomInteger(m_min, element, m_seed, m_random.Duplicate());
		}
	}
}