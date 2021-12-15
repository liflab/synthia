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
package ca.uqac.lif.synthia.test;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.GiveUpException;
import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * @ingroup Examples
 * @author sylvain
 *
 * @param <T>
 */
public class Assert<T>
{
	/**
	 * The maximum number of times the process of finding an <em>initial</em>
	 * failing test input can be restarted.
	 */
	protected static final int MAX_STARTS = 100;

	/**
	 * The maximum number of times the shrinking process can be repeated before
	 * stopping.
	 */
	protected static final int MAX_CYCLES = 100;

	/**
	 * The maximum number of times an input is asked in each phase.
	 */
	protected static final int MAX_TRIES = 10;
	
	/**
	 * The object that is being tested.
	 */
	protected Testable m_sut;

	/**
	 * A picker producing test inputs that can be shrunk.
	 */
	protected Shrinkable<T> m_input;

	/**
	 * The list of progressively smaller values collected during the shrinking
	 * process.
	 */
	protected List<T> m_shrunk;

	/**
	 * A float picker used as a source of choice. This picker is merely passed
	 * to the shrinkable picker in its calls to
	 * {@link Shrinkable#shrink(Object, Picker, float)}.
	 */
	protected Picker<Float> m_decision;

	/**
	 * Creates a new assertion object.
	 * @param sut The object that is being tested
	 * @param input A picker producing test inputs that can be shrunk
	 * @param decision A float picker used as a source of choice
	 */
	public Assert(Testable sut, Shrinkable<T> input, Picker<Float> decision)
	{
		super();
		m_sut = sut;
		m_input = input;
		m_shrunk = new ArrayList<T>();
		m_decision = decision;
	}

	/**
	 * Creates a new assertion object.
	 * @param sut The object that is being tested
	 * @param input A picker producing test inputs that can be shrunk
	 */
	public Assert(Testable sut, Shrinkable<T> input)
	{
		this(sut, input, RandomFloat.instance);
	}

	/**
	 * Gets the total number of shrinking iterations conducted by the object.
	 * @return The number of iterations
	 */
	public List<T> getIterations()
	{
		return m_shrunk;
	}

	/**
	 * Gets the initial input causing the test to fail.
	 * @return The input
	 */
	public T getInitial()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(0);
	}

	/**
	 * Gets the final input causing the test to fail at the end of the shrinking
	 * process.
	 * @return The input
	 */
	public T getShrunk()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(m_shrunk.size() - 1);
	}

	/**
	 * Starts the search for a failing test input.
	 * @return The value {@code false} if a failing input has been found,
	 * {@code true} otherwise. 
	 */
	@SuppressWarnings("unchecked")
	public boolean check()
	{
		T best = null;
		for (int start_cnt = 0; start_cnt < MAX_STARTS; start_cnt++)
		{
			List<T> shrunk = new ArrayList<T>();
			T o = null;
			boolean found = false;
			try
			{
				for (int i = 0; i < MAX_TRIES; i++)
				{
					o = m_input.pick();
					if (!m_sut.test(o))
					{
						found = true;
						break;
					}
				}
				shrunk.add(o);
			}
			catch (GiveUpException e)
			{
				continue;
			}
			catch (NoMoreElementException e)
			{
				continue;
			}
			if (!found)
			{
				continue;
			}
			Shrinkable<T> p = m_input.shrink(o, m_decision, 1);
			try
			{
				for (int i = 0; i < MAX_CYCLES; i++)
				{
					boolean new_found = false;
					for (float magnitude = 0.25f; !new_found && magnitude <= 1; magnitude += 0.25f)
					{
						p = p.shrink(o, m_decision, magnitude);
						int j;
						for (j = 0; j < MAX_TRIES; j++)
						{
							o = p.pick();
							if (!m_sut.test(o))
							{
								shrunk.add(o);
								new_found = true;
								break;
							}
						}
					}
				}
			}
			catch (NoMoreElementException e)
			{
				// Nothing to do
			}
			if (!(o instanceof Comparable))
			{
				// No point in trying to find "best" input
				m_shrunk = shrunk;
				break;
			}
			if (!shrunk.isEmpty())
			{
				Comparable<T> new_o = (Comparable<T>) shrunk.get(shrunk.size() - 1);
				if (best == null || new_o.compareTo(best) < 0)
				{
					// Found a "smaller" input
					m_shrunk = shrunk;
					best = shrunk.get(shrunk.size() - 1);
				}				
			}
		}
		return m_shrunk.isEmpty();
	}
}
