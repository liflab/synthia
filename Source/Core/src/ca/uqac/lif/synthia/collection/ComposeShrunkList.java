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
package ca.uqac.lif.synthia.collection;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * Given a reference list, picks lists that are smaller.
 * Given a list <i>L</i>, a list <i>L</i>' is "smaller" list if either:
 * <ul>
 * <li><i>L</i>' has strictly fewer elements than <i>L</i>, or</li>
 * <li><i>L</i>' has the same number of elements <i>n</i>, and there
 * exists an index <i>i</i> &leq; <i>n</i> such that <i>L</i>[<i>i</i>] &lt;
 * <i>L</i>'[<i>i</i>] and <i>L</i>[<i>j</i>] = <i>L</i>'[<i>j</i>] for
 * all <i>j</i> &lt; <i>i</i>
 * </ul>
 * @author Sylvain Hallé
 *
 * @param <T> The type of the elements in the list
 */
public class ComposeShrunkList<T> implements Picker<List<T>>, Shrinkable<List<T>>
{
	protected Shrinkable<T> m_originalPicker;
	
	protected Shrinkable<Integer> m_length;
	
	protected List<T> m_reference;
	
	protected Picker<Float> m_decision;
	
	protected float m_magnitude;
	
	public ComposeShrunkList(Shrinkable<T> element_picker, Shrinkable<Integer> length, List<T> reference, Picker<Float> decision, float magnitude)
	{
		super();
		m_originalPicker = element_picker;
		m_reference = reference;
		m_decision = decision;
		m_length = length;
		m_magnitude = magnitude;
	}
	
	public ComposeShrunkList(Shrinkable<T> element_picker, Shrinkable<Integer> length, List<T> reference)
	{
		this(element_picker, length, reference, RandomFloat.instance, 1);
	}
	
	@Override
	public Shrinkable<List<T>> shrink(List<T> list, Picker<Float> d, float magnitude)
	{
		return new ComposeShrunkList<T>(m_originalPicker, m_length, list, d, magnitude);
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> pick()
	{
		int ref_size = m_reference.size();
		if (m_decision.pick() < (1 - m_magnitude))
		{
			// Generate a shorter list
			int new_length = m_length.shrink(ref_size, m_decision, m_magnitude).pick();
			List<T> new_list = new ComparableList<T>();
			for (int i = 0; i < new_length; i++)
			{
				new_list.add(m_originalPicker.pick());
			}
			return new_list;
		}
		// Generate a list with smaller elements
		int prefix_length = (int) (ref_size * m_decision.pick() * (1 - m_magnitude));
		List<T> new_list = new ComparableList<T>();
		for (int i = 0; i < ref_size; i++)
		{
			if (i < prefix_length)
			{
				if (m_decision.pick() < m_magnitude)
				{
					new_list.add(m_reference.get(i));
				}
				else
				{
					Picker<T> shrunk = m_originalPicker.shrink(m_reference.get(i), m_decision, m_magnitude);
					try
					{
						new_list.add(shrunk.pick());
					}
					catch (NoMoreElementException e)
					{
						// This element can no longer be shrunk, leave it alone
						new_list.add(m_reference.get(i));
					}
				}
			}
			else if (i == prefix_length)
			{
				Picker<T> shrunk = m_originalPicker.shrink(m_reference.get(i), m_decision, m_magnitude);
				try
				{
					new_list.add(shrunk.pick());
				}
				catch (NoMoreElementException e)
				{
					// This element can no longer be shrunk, leave it alone
					new_list.add(m_reference.get(i));
				}
			}
			else
			{
				new_list.add(m_originalPicker.pick());
			}
		}
		return new_list;
	}

	@Override
	public Picker<List<T>> duplicate(boolean with_state)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shrinkable<List<T>> shrink(List<T> o)
	{
		return shrink(o, RandomFloat.instance, 1);
	}
}
