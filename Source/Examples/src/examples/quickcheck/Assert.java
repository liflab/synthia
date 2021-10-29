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
	protected static final int MAX_CYCLES = 1000;
	
	protected static final int MAX_TRIES = 1000000;
	
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

	public boolean check()
	{
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
			m_shrunk.add(o);
		}
		catch (GiveUpException e)
		{
			return true;
		}
		catch (NoMoreElementException e)
		{
			return true;
		}
		if (!found)
		{
			return true;
		}
		Shrinkable<T> p = m_input.shrink(o, m_decision);
		try
		{
			for (int i = 0; i < MAX_CYCLES; i++)
			{
				for (int j = 0; j < MAX_TRIES; j++)
				{
					o = p.pick();
					if (!evaluate(o))
					{
						m_shrunk.add(o);
						break;
					}
				}
				p = p.shrink(o, m_decision);
			}
		}
		catch (NoMoreElementException e)
		{
			// Nothing to do
		}
		return false;
	}

	protected boolean evaluate(T o)
	{
		return true;
	}
}
