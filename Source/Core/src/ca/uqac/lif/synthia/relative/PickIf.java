/*
    Synthia, a data structure generator
    Copyright (C) 2019-2023 Laboratoire d'informatique formelle
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
package ca.uqac.lif.synthia.relative;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.synthia.GiveUpException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.explanation.NthSuccessiveOutput;
import ca.uqac.lif.synthia.util.Mutator;

/**
 * Returns object from a picker satisfying a condition.
 *
 * @param <T> The object type returned by the picker.
 * @ingroup API
 */
public class PickIf<T> extends Mutator<T> implements ExplanationQueryable
{
	/**
	 * The maximal number of iteration that the while loop of the {@link #pick()} method can do.
	 * If the value is negative, there will be no maximum number of iterations.
	 */
	protected int m_maxIteration;
	
	/**
	 * A list keeping track of the number of rejected elements between each
	 * output element. This list is only used to answer calls to {@link #query()}.
	 */
	/*@ non_null @*/ protected List<Integer> m_rejected;

	/**
	 * Constructor with default {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 */
	public PickIf(Picker<? extends T> picker)
	{
		super(picker);
		m_maxIteration = 10000;
		m_rejected = new ArrayList<Integer>();
		m_rejected.add(0);
	}

	/**
	 * Constructor who takes a {@link #m_maxIteration} value.
	 *
	 * @param picker The picker used to generate objects.
	 * @param max_iteration The maximum number of iterations the {@link #pick()} will try to generate
	 *                      an object before giving up.
	 */
	public PickIf(Picker<? extends T> picker, int max_iteration)
	{
		super(picker);
		m_maxIteration = max_iteration;
	}

	/**
	 * Method to evaluate if an element is satisfying a condition. The difference
	 * with {@link #canChoose(Object) canChoose()} is that this method keeps
	 * track of the number of rejected elements between each element that is let
	 * through.
	 *
	 * @param element The element to check
	 * @return <tt>true</tt> if the condition is satisfied and <tt>false</tt> if it's not the case.
	 */
	protected boolean canChoose(T element)
	{
		int index = m_rejected.size() - 1;
		if (!select(element))
		{
			m_rejected.set(index, m_rejected.get(index) + 1);
			return false;
		}
		m_rejected.add(0);
		return true;
	}
	
	/**
	 * Method to evaluate if an element is satisfying a condition. Descendants
	 * of this class must override this method to define a selection criterion.
	 *
	 * @param element The element to check
	 * @return <tt>true</tt> if the condition is satisfied and <tt>false</tt> if it's not the case.
	 */
	protected boolean select(T element)
	{
		return true;
	}
	
	protected void copyInto(PickIf<T> m, boolean with_state)
	{
		super.copyInto(m, with_state);
		if (with_state)
		{
			m.m_rejected.addAll(m_rejected);
		}
		
	}

	/**
	 * Method to pick the first object generated by the picker who satisfies the condition.
	 * WARNING: For now, this method can result in a infinite loop if the picker can't generate
	 * an object who satisfies the condition.
	 *
	 * @return The object.
	 */
	public T pick()
	{
		int iteration_counter = 0;
		T picked_value = m_picker.pick();
		while (!canChoose(picked_value))
		{
			if (checkIfInfiniteLoop(iteration_counter))
			{
				throw new GiveUpException();
			}
			picked_value = m_picker.pick();
			iteration_counter++;
		}
		return picked_value;
	}

	/**
	 * Protected method to check if the instance of the class detects that the while loop of
	 * the {@link #pick()} method fell into an infinite loop by supposing that if the loop exceed
	 * a maximal number of iteration, it will cause an infinite loop.
	 *
	 * @warning If {@link #m_maxIteration} is negative, the {@link #pick} method will not check if
	 * the iteration counter has exceeded the value of {@link #m_maxIteration} and this
	 * method will always return false. This means that it's possible that calling {@link #pick}
	 * causes an infinite loop. Only use a negative value for {@link #m_maxIteration} if you assume
	 * that calling {@link #pick} could cause an infinite loop.
	 *
	 * @return <tt>true</tt> if infinite loop is detected and <tt>false</tt> if it's not the case.
	 */
	protected boolean checkIfInfiniteLoop(int iteration_counter)
	{
		if (m_maxIteration < 0)
		{
			return false;
		}
		else
		{
			return !(iteration_counter < m_maxIteration);
		}
	}
	
	@Override
	public PickIf<T> duplicate(boolean with_state)
	{
		PickIf<T> pif = new PickIf<T>(m_picker.duplicate(with_state), m_maxIteration);
		copyInto(pif, with_state);
		return pif;
	}

	@Override
	public void reset()
	{
		m_picker.reset();
		m_rejected.clear();
		m_rejected.add(0);
	}

	@Override
	public PartNode getExplanationForOutput(int index, Part p, NodeFactory f)
	{
		PartNode root = f.getPartNode(p, this);
		if (index >= m_rejected.size() - 1)
		{
			// Not a valid part, end there
			return root;
		}
		int current_index = m_rejected.get(0);
		for (int i = 1; i < index; i++)
		{
			current_index += m_rejected.get(i);
		}
		Part new_p = NthSuccessiveOutput.replaceOutIndexBy(p, current_index + index);
		root.addChild(f.getPartNode(new_p, m_picker));
		return root;
	}
	
	@Override
	public String toString()
	{
		return "PickIf";
	}
}
