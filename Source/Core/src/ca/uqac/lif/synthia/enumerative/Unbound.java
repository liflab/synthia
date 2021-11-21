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
package ca.uqac.lif.synthia.enumerative;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;

/**
 * Takes a bounded picker and turns it into an unbounded one. This is done by
 * relaying values from the original picker as long as it does not throw a
 * {@link NoMoreElementException}. Then, subsequent calls to {@link #pick()}
 * repeat previous values.
 * @author Sylvain Hallé
 *
 * @param <T> The type of the elements produced
 * @ingroup API
 */
public class Unbound<T> implements Picker<T>
{
	/*@ non_null @*/ protected Picker<T> m_source;
	
	/*@ non_null @*/ protected Picker<Float> m_floatSource;
	
	/*@ non_null @*/ protected List<T> m_previousValues;
	
	protected boolean m_sourceDone;
	
	public Unbound(/*@ non_null @*/ Picker<T> source, Picker<Float> float_source)
	{
		super();
		m_source = source;
		m_floatSource = float_source;
		m_previousValues = new ArrayList<T>();
		m_sourceDone = false;
	}

	@Override
	public void reset()
	{
		m_previousValues.clear();
		m_source.reset();
		m_floatSource.reset();
	}

	@Override
	public T pick()
	{
		if (m_sourceDone)
		{
			return pickFromList();
		}
		try
		{
			T t = m_source.pick();
			if (!m_previousValues.contains(t))
			{
				m_previousValues.add(t);
			}
			return t;
		}
		catch (NoMoreElementException e)
		{
			m_sourceDone = true;
			return pickFromList();
		}
	}
	
	protected T pickFromList()
	{
		if (m_previousValues.isEmpty())
		{
			throw new NoMoreElementException();
		}
		int index = (int) Math.floor((float) m_previousValues.size() * m_floatSource.pick());
		return m_previousValues.get(index);
	}

	@Override
	public Unbound<T> duplicate(boolean with_state)
	{
		Unbound<T> u = new Unbound<T>(m_source.duplicate(with_state), m_floatSource.duplicate(with_state));
		if (with_state)
		{
			u.m_sourceDone = m_sourceDone;
			u.m_previousValues.addAll(m_previousValues);
		}
		return u;
	}
}
