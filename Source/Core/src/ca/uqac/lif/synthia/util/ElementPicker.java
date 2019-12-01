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
package ca.uqac.lif.synthia.util;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;

/**
 * Picks an element from a collection, where the probability of picking
 * each element can be user-defined. Elements are added to the collection
 * by calling {@link #add(Object, Number) add()}. For example, the following
 * code snippet creates a collection of three strings, where <tt>"A"</tt>
 * has twice the odds of being picked compared to <tt>"B"</tt> and <tt>"C"</tt>:
 * <pre>
 * RandomFloat r = new RandomFloat();
 * ElementPicker&lt;String&gt; ep = new ElementPicker&lt;String&gt;(r);
 * ep.add("A", 0.5).add("B", 0.25).add("C", 0.25);</pre>
 * When adding elements, one must make sure that the sum of probabilities
 * is equal to 1.
 * @param <T> The type of the object to pick
 */
public class ElementPicker<T> implements Picker<T>
{
	/**
	 * A list storing each element with its associated probability
	 */
	/*@ non_null @*/ protected List<ProbabilityChoice<T>> m_choices;
	
	/**
	 * A picker used to choose the element
	 */
	/*@ non_null @*/ protected Picker<Float> m_floatPicker;
	
	/**
	 * Creates a new element picker
	 * @param picker A picker used to choose the element
	 */
	public ElementPicker(/*@ non_null @*/ Picker<Float> picker)
	{
		super();
		m_choices = new ArrayList<ProbabilityChoice<T>>();
		m_floatPicker = picker;
	}
	
	/**
	 * Adds an object-probability association
	 * @param pc The association
	 * @return This element picker
	 */
	/*@ non_null @*/ public ElementPicker<T> add(/*@ non_null @*/ ProbabilityChoice<T> pc)
	{
		m_choices.add(pc);
		return this;
	}
	
	/**
	 * Adds an object-probability association
	 * @param t The object
	 * @param p The probability, must be between 0 and 1
	 * @return This element picker
	 */
	/*@ non_null @*/ public ElementPicker<T> add(/*@ non_null @*/ T t, /*@ non_null @*/ Number p)
	{
		ProbabilityChoice<T> pc = new ProbabilityChoice<T>(t, p);
		return add(pc);
	}
	
	@Override
	public void reset()
	{
		// Nothing to do
	}

	@Override
	public T pick() 
	{
		float[] probs = new float[m_choices.size()];
		float total_prob = 0f;
		for (int i = 0; i < probs.length; i++)
		{
			total_prob += m_choices.get(i).getProbability();
			probs[i] = total_prob;
		}
		float f = m_floatPicker.pick();
		int index = 0;
		while (index < probs.length)
		{
			if (f <= probs[index])
			{
				break;
			}
			index++;
		}
		if (index >= m_choices.size())
		{
			return null;
		}
		return m_choices.get(index).getObject();
	}

	@Override
	/*@ pure non_null @*/ public ElementPicker<T> duplicate(boolean with_state)
	{
		ElementPicker<T> ep = new ElementPicker<T>(m_floatPicker.duplicate(with_state));
		for (ProbabilityChoice<T> pc : m_choices)
		{
			ep.m_choices.add(pc.duplicate(with_state));
		}
		return ep;
	}
	
	/**
	 * Simple data structure asssociating an object with
	 * a probability.
	 * @param <T> The type of the object to which a probability is associated
	 */
	public static class ProbabilityChoice<T>
	{
		/**
		 * The probability of taking this object
		 */
		protected float m_probability;
		
		/**
		 * The object corresponding to that object
		 */
		/*@ non_null @*/ protected T m_object;
		
		/**
		 * Creates a new probability-object association
		 * @param t The object
		 * @param probability The probability of picking this node
		 */
		public ProbabilityChoice(/*@ non_null @*/ T t, /*@ non_null @*/ Number p)
		{
			super();
			m_object = t;
			m_probability = p.floatValue();
		}
		
		/**
		 * Gets the probability for this association
		 * @return The probability
		 */
		/*@ pure @*/ public float getProbability()
		{
			return m_probability;
		}
		
		/**
		 * Gets the tree node for this association
		 * @return The node
		 */
		/*@ pure non_null @*/ public T getObject()
		{
			return m_object;
		}
		
		@Override
		public String toString()
		{
			return m_object.toString() + " (" + m_probability + ")";
		}
		
		/**
		 * Duplicates this probability-node association
		 * @param with_state If set to <tt>true</tt>, the node is duplicated
		 * with its state
		 * @return A duplicate of this association
		 */
		/*@ pure non_null @*/ public ProbabilityChoice<T> duplicate(boolean with_state)
		{
			return new ProbabilityChoice<T>(m_object, m_probability);
		}
	}
}
