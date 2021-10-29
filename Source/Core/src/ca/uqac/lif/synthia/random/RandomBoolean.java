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
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.NothingPicker;

/**
 * Picks a Boolean value. This class actually implements a Bernoulli
 * trial, with the possibility of setting the probability of success
 * <i>p</i>.
 * 
 * @ingroup API
 */
public class RandomBoolean extends RandomPicker<Boolean> implements Shrinkable<Boolean>
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
	 * Private constructor used in the duplicate method.
	 * @param true_probability The probability of picking <tt>true</tt>.
	 * @param seed The initial seed of the random genarator.
	 * @param random The random generator.
	 */
	private RandomBoolean(Number true_probability, int seed, Random random)
	{
		m_trueProbability = true_probability.floatValue();
		m_seed = seed;
		m_random = random;
	}

	/**
	 * Creates a new instance of the picker, with a 50-50 chance of
	 * producing <tt>true</tt> or <tt>false</tt>
	 */
	public RandomBoolean()
	{
		this(0.5);
	}


	/**
	 * Picks a random boolean. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The random boolean.
	 */
	@Override
	public Boolean pick()
	{
		return m_random.nextFloat() <= m_trueProbability;
	}


	/**
	 * Creates a copy of the RandomBoolean picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the RandomBoolean picker
	 */
	@Override
	public RandomBoolean duplicate(boolean with_state) 
	{
		RandomBoolean copy = new RandomBoolean(m_trueProbability, m_seed, m_random.Duplicate());

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}

	@Override
	public Shrinkable<Boolean> shrink(Boolean o, Picker<Float> decision, float magnitude)
	{
		// We assume an ordering of Booleans where false < true
		if (o)
		{
			return new Constant<Boolean>(false);
		}
		return new NothingPicker<Boolean>();
	}

	@Override
	public Shrinkable<Boolean> shrink(Boolean o)
	{
		return shrink(o, RandomFloat.instance, 1);
	}
}
