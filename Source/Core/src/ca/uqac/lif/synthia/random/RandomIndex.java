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

import ca.uqac.lif.synthia.IndexPicker;
import ca.uqac.lif.synthia.random.generators.Random;

public class RandomIndex extends RandomInteger implements IndexPicker
{

	/**
	 * Creates a new instance of the picker without parameters.
	 */
	public RandomIndex()
	{
		this(0, 1);
	}


	/**
	 * Creates a new instance of the picker with a specified interval.
	 * @param min The lower bound of the interval.
	 * @param max The higher bound of the interval.
	 */
	public RandomIndex(int min, int max) 
	{
		super(min, max);
	}

	/**
	 * Private constructor used to duplicate the picker.
	 * @param min The lower bound of the interval.
	 * @param max The higher bound of the interval.
	 * @param seed The initial seed of the random genarator.
	 * @param random The random generator.
	 */
	private RandomIndex(int min, int max, int seed, Random random) {super(min, max, seed, random);}

	/**
	 * Sets the interval in which integers are picked. The interval is in the [0,size] range.
	 * @param size The higher bound of the interval.
	 * @return The instance of the RandomIndex class in which the present method has been invoked.
	 */
	@Override
	public RandomIndex setRange(int size)
	{
		setInterval(0, size);
		return this;
	}


	/**
	 * Creates a copy of the RandomIndex picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomIndex picker
	 */
	@Override
	public RandomIndex duplicate(boolean with_state)
	{
		RandomIndex copy = new RandomIndex(m_min, m_max, m_seed, m_random.Duplicate());

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}
}
