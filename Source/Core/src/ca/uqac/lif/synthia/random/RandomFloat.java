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
import ca.uqac.lif.synthia.relative.NothingPicker;
import ca.uqac.lif.synthia.relative.RelativePicker;



/**
 * Picks a floating point number uniformly in an interval
 */
public class RandomFloat extends RandomPicker<Float> implements RelativePicker<Float>

{
	/**
	 * The lower bound of the interval
	 */
	protected float m_min;
	
	/**
	 * The higher bound of the interval
	 */
	protected float m_max;

	/**
	 * Default constructor.
	 */
	public RandomFloat()
	{
		super();
		m_min = 0;
		m_max = 1;
	}


	/**
	 * Creates a new instance of the picker.
	 * @param min The lower bound of the interval
	 * @param max The higher bound of the interval
	 */
	public RandomFloat(Number min, Number max)
	{
		super();
		m_min = min.floatValue();
		m_max = max.floatValue();
	}

	/**
	 * Private constructor used to duplicate the picker.
	 * @param min The lower bound of the interval.
	 * @param max The higher bound of the interval.
	 * @param seed The initial seed of the random genarator.
	 * @param random The random generator.
	 */
	private RandomFloat(Number min, Number max, int seed, Random random)
	{
		m_min = min.floatValue();
		m_max = max.floatValue();
		m_seed = seed;
		m_random = random;
	}

	/**
	 * Picks a random float in the specified interval. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random float
	 */
	@Override
	public Float pick()
	{
		return m_random.nextFloat() * (m_max - m_min) + m_min;
	}

	/**
	 * Creates a copy of the RandomIntervalFloat picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomIntervalFloat picker
	 */
	@Override
	public RandomFloat duplicate(boolean with_state)
	{
		RandomFloat copy = new RandomFloat(m_min, m_max, m_seed, m_random.Duplicate());

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}

	/**
	 * Create a new {@link RandomFloat} picker guaranteeing to produce lower values than the one
	 * taken as input. If the input is lower or equal to the {@link #m_min} attribute value, the
	 * method will return a {@link ca.uqac.lif.synthia.relative.NothingPicker}.
	 *
	 * @param element The value to which those which will be produced by the new instance of the class
	 *                must be lower.
	 *
	 * @return The new instance of the class or a {@link ca.uqac.lif.synthia.relative.NothingPicker}.
	 */
	@Override
	public RelativePicker<Float> getPicker(Float element)
	{
		if((element <= m_min) || (element.isNaN()))
		{
			return new NothingPicker();
		}
		else
		{
			return new RandomFloat(m_min, element);
		}
	}

	@Override
	public int compare(Object old_value, Object new_value)
	{
		if ((Float)new_value < (Float)old_value)
		{
			return -1;
		}
		else if ((Float)new_value == (Float)old_value)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
}