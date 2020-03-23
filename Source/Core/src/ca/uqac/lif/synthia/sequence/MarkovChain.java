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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.PickerException;

/**
 * Generates a sequence of objects by a random walk in a Markov chain.
 * A <a href="https://en.wikipedia.org/wiki/Markov_chain">Markov chain</a>
 * is a stochastic model describing a sequence of possible events. Each state
 * of the chain is associated to a {@link Picker}; transitions between states
 * A &rarr; B are labelled with the probability of going to B when the
 * current state ia A. The following picture shows an example of a Markov
 * chain: 
 * <p>
 * <img src="{@docRoot}/doc-files/Markov.png" alt="Markov chain">
 * <p>
 * 
 * @param <T> The type of objects returned by the Markov chain. The pickers
 * associated to each state must return objects of type <tt>T</tt> or its
 * descendants.
 */
public class MarkovChain<T> implements Picker<T> 
{
	/**
	 * The index of the current state of the machine
	 */
	protected int m_currentState;
	
	/**
	 * The pickers associated to each state
	 */
	protected Map<Integer,Picker<? extends T>> m_pickers;
	
	/**
	 * A map associating state numbers with the list of their outgoing
	 * transitions
	 */
	protected Map<Integer,List<Transition>> m_transitions;
	
	/**
	 * A source of numbers between 0 and 1. This source is used to pick the
	 * next transition to take. 
	 */
	protected Picker<Float> m_floatSource;
	
	/**
	 * Whether to exhaust each state before moving to another
	 */
	protected boolean m_exhaust;
	
	/**
	 * Creates a new empty Markov chain.
	 * @param float_source A source of numbers between 0 and 1. This source is
	 * used to pick the next transition to take. 
	 */
	public MarkovChain(Picker<Float> float_source)
	{
		super();
		m_transitions = new HashMap<Integer,List<Transition>>();
		m_pickers = new HashMap<Integer,Picker<? extends T>>();
		m_floatSource = float_source;
		m_exhaust = false;
	}
		
	/**
	 * Adds a new transition to the machine
	 * @param source The source state
	 * @param destination The destination state
	 * @param probability The probability of taking this transition (between 0 and 1)
	 * @return This machine
	 */
	public MarkovChain<T> add(int source, int destination, Number probability)
	{
		Transition t = new Transition(destination, probability.floatValue());
		List<Transition> trans = m_transitions.get(source);
		if (trans == null)
		{
			trans = new ArrayList<Transition>();
			m_transitions.put(source, trans);
		}
		trans.add(t);
		return this;
	}
	
	/**
	 * Associates a picker to a state 
	 * @param state The state
	 * @param p The picker
	 * @return This machine
	 */
	public MarkovChain<T> add(int state, Picker<T> p)
	{
		m_pickers.put(state, p);
		return this;
	}
	
	/**
	 * Sets whether to exhaust the picker associated to a state before
	 * transitioning to another state
	 * @param b Set to <tt>true</tt> to exhaust each picker, <tt>false</tt>
	 * otherwise
	 * @return This Markov chain
	 */
	public MarkovChain<T> exhaust(boolean b)
	{
		m_exhaust = b;
		return this;
	}
	
	@Override
	public T pick()
	{
		if (m_exhaust)
		{
			T t = m_pickers.get(m_currentState).pick();
			if (t != null)
			{
				return t;
			}
		}
		float p = m_floatSource.pick();
		int new_state = selectTransition(p);
		if (new_state < 0)
		{
			return null;
		}
		Picker<? extends T> lp = m_pickers.get(new_state);
		if (lp == null)
		{
			throw new PickerException("State " + new_state + " does not have a picker");
		}
		m_currentState = new_state;
		if (m_exhaust)
		{
			lp.reset();
		}
		return lp.pick();
	}
	
	/**
	 * From the current state, randomly selects a destination state based on
	 * the probabilities associated to each outgoing transition
	 * @param p A random value between 0 and 1, used to pick the next state
	 * @return The number of the state, or -1 if no destination was
	 * chosen
	 */
	protected int selectTransition(float p)
	{
		List<Transition> transitions = m_transitions.get(m_currentState);
		float sum_prob = 0f;
		int last_state = -1;
		for (Transition t : transitions)
		{
			sum_prob += t.getProbability();
			last_state = t.getDestination();
			if (p <= sum_prob)
			{
				break;
			}
		}
		return last_state;
	}

	@Override
	public MarkovChain<T> duplicate(boolean with_state)
	{
		MarkovChain<T> mmm = new MarkovChain<T>(m_floatSource);
		mmm.m_transitions.putAll(m_transitions);
		mmm.m_pickers.putAll(m_pickers);
		mmm.m_exhaust = m_exhaust;
		if (with_state)
		{
			mmm.m_currentState = m_currentState;
		}
		return mmm;
	}

	@Override
	public void reset()
	{
		m_currentState = 0;
	}
	
	/**
	 * Produces a rendition of the machine as Graphviz input file.
	 * @param out The print stream to which the graph is written
	 */
	public void toDot(PrintStream out)
	{
		out.println("digraph G {");
		out.println(" node [style=filled];");
		for (Map.Entry<Integer,Picker<? extends T>> e : m_pickers.entrySet())
		{
			out.print(" ");
			out.print(e.getKey());
			out.print(" [label=\"");
			out.print(e.getValue().toString());
			out.println("\"];");
		}
		for (Map.Entry<Integer,List<Transition>> e : m_transitions.entrySet())
		{
			for (Transition t : e.getValue())
			{
				out.print(" ");
				out.print(e.getKey());
				out.print(" -> ");
				out.print(t.getDestination());
				out.print(" [label=\"");
				out.print(t.getProbability());
				out.println("\"];");
			}			
		}
		out.println("}");
	}
	
	/**
	 * Representation of a probabilistic transition in the state machine
	 */
	public static class Transition
	{
		protected int m_destination;
		
		protected float m_probability;
		
		/**
		 * Creates a new transition
		 * @param destination The destination state
		 * @param probability The probability of taking this transition (between 0 and 1)
		 */
		public Transition(int destination, float probability)
		{
			super();
			m_destination = destination;
			m_probability = probability;
		}
		
		public int getDestination()
		{
			return m_destination;
		}
		
		public float getProbability()
		{
			return m_probability;
		}
		
		@Override
		public String toString()
		{
			return "-> " + m_destination + " (" + m_probability + ")";
		}
	}	
}
