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
package examples.quickcheck;

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
	protected static final int MAX_STARTS = 100;

	protected static final int MAX_CYCLES = 100;

	protected static final int MAX_TRIES = 10;

	protected Shrinkable<T> m_input;

	protected List<T> m_shrunk;

	protected Picker<Float> m_decision;

	public Assert(Shrinkable<T> input, Picker<Float> decision)
	{
		super();
		m_input = input;
		m_shrunk = new ArrayList<T>();
		m_decision = decision;
	}

	public Assert(Shrinkable<T> input)
	{
		this(input, RandomFloat.instance);
	}

	public List<T> getIterations()
	{
		return m_shrunk;
	}

	public T getInitial()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(0);
	}

	public T getShrunk()
	{
		if (m_shrunk.isEmpty())
		{
			return null;
		}
		return m_shrunk.get(m_shrunk.size() - 1);
	}

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
					if (!evaluate(o))
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
							if (!evaluate(o))
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

	protected boolean evaluate(T o)
	{
		return true;
	}
}
