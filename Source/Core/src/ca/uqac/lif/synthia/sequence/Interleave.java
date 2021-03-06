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
package ca.uqac.lif.synthia.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.ElementPicker.ProbabilityChoice;

public class Interleave<T> implements Picker<T>
{
	protected Map<Integer,Picker<T>> m_sessions;

	protected List<ProbabilityChoice<Picker<T>>> m_choices;

	protected int m_idCount;

	/**
	 * The probability to start a new session at any moment
	 */
	protected float m_midProbability;

	/**
	 * The probability to start a new session when one has just finished
	 */
	protected float m_endProbability;
	
	/**
	 * A source of floating point numbers
	 */
	protected Picker<Float> m_floatSource;
	
	/**
	 * Maximum number of tries to get a new element from a session before
	 * giving up
	 */
	protected static final transient int MAX_TRIES = 1000;

	public Interleave(Picker<Float> float_source, Number mid_probability, Number end_probability)
	{
		super();
		m_sessions = new HashMap<Integer,Picker<T>>();
		m_floatSource = float_source;
		m_choices = new ArrayList<ProbabilityChoice<Picker<T>>>();
		m_midProbability = mid_probability.floatValue();
		m_endProbability = end_probability.floatValue();
		m_idCount = 0;
	}

	public Interleave<T> add(Picker<T> provider, Number probability)
	{
		m_choices.add(new ProbabilityChoice<Picker<T>>(provider, probability.floatValue()));
		return this;
	}

	@Override
	public void reset()
	{
		m_sessions.clear();
		m_idCount = 0;
	}

	@Override
	public T pick() 
	{
		float p = m_floatSource.pick();
		if (m_sessions.isEmpty() || p < m_midProbability)
		{
			int new_sess_id = ++m_idCount;
			m_sessions.put(new_sess_id, startNewSession());
		}
		// Pick a session
		T next = null;
		for (int i = 0; i < MAX_TRIES && next == null; i++)
		{
			List<Integer> ids = new ArrayList<Integer>(m_sessions.size());
			ids.addAll(m_sessions.keySet());
			int pos = (int) (m_floatSource.pick() * ids.size());
			int sess_id = ids.get(pos);
			Picker<T> prov = m_sessions.get(sess_id);
			next = prov.pick();
			if (next == null)
			{
				m_sessions.remove(sess_id);
				if (p < m_endProbability)
				{
					int new_sess_id = ++m_idCount;
					m_sessions.put(new_sess_id, startNewSession());
				}
			}
		}
		return next;
	}

	@Override
	public Interleave<T> duplicate(boolean with_state)
	{
		Interleave<T> ilp = new Interleave<T>(m_floatSource, m_midProbability, m_endProbability);
		ilp.m_choices.addAll(m_choices);
		if (with_state)
		{
			ilp.m_idCount = m_idCount;
			for (Map.Entry<Integer,Picker<T>> e : m_sessions.entrySet())
			{
				ilp.m_sessions.put(e.getKey(), e.getValue().duplicate(with_state));
			}
		}
		return ilp;
	}

	protected Picker<T> startNewSession()
	{
		float f = m_floatSource.pick();
		float sum_prob = 0;
		for (ProbabilityChoice<Picker<T>> pc : m_choices)
		{
			sum_prob += pc.getProbability();
			if (f <= sum_prob)
			{
				return pc.getObject().duplicate(false);
			}
		}
		return null;
	}
}
