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

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Seedable;

/**
 * Picks an object based on the value of a random number generator. All
 * descendants of this class use an instance of Java's {@link Random}
 * class. They also implement the {@link Seedable} interface, which means
 * that this internal generator can be seeded with some value; furthermore,
 * a call to {@link #reset()} will instantiate a new instance of
 * <tt>Random</tt> with the initial seed. This means that resetting a
 * <tt>RandomPicker</tt> should in principle make it output the same sequence
 * of values as when it was first created.
 *
 * @param <T> The type of object to pick
 */
public abstract class RandomPicker<T> implements Picker<T>, Seedable
{
	/**
	 * A random number generator used to set the seed when none is
	 * specified
	 */
	protected static final transient Random s_random = new Random();

	/*@ non_null @*/ protected transient Random m_random;

	protected int m_seed;

	public RandomPicker(int seed)
	{
		super();
		setSeed(seed);
	}

	public RandomPicker() { this(s_random.nextInt()); }

	@Override
	public void setSeed(int seed)
	{
		m_seed = seed;
		m_random = new Random(seed);
	}

	@Override
	public void reset()
	{
		m_random = new Random(m_seed);
	}
}