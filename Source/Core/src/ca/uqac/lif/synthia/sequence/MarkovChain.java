package ca.uqac.lif.synthia.sequence;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqac.lif.cep.ProcessorException;
import ca.uqac.lif.synthia.Picker;

public class MarkovChain<T> implements Picker<T> 
{
	protected int m_currentState;
	
	protected Map<Integer,Picker<T>> m_providers;
	
	protected Map<Integer,List<Transition>> m_transitions;
	
	protected Picker<Float> m_floatSource;
	
	public MarkovChain(Picker<Float> float_source)
	{
		super();
		m_transitions = new HashMap<Integer,List<Transition>>();
		m_providers = new HashMap<Integer,Picker<T>>();
		m_floatSource = float_source;
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
	 * Associates a provider to a state 
	 * @param state The state
	 * @param p The provider
	 * @return This machine
	 */
	public MarkovChain<T> add(int state, Picker<T> p)
	{
		m_providers.put(state, p);
		return this;
	}
	
	@Override
	public T pick()
	{
		float p = m_floatSource.pick();
		int new_state = selectTransition(p);
		if (new_state < 0)
		{
			return null;
		}
		Picker<T> lp = m_providers.get(new_state);
		if (lp == null)
		{
			throw new ProcessorException("State " + new_state + " does not have a line provider");
		}
		m_currentState = new_state;
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
		mmm.m_providers.putAll(m_providers);
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
	public String toDot(PrintStream out)
	{
		out.println("digraph G {");
		out.println(" node [style=filled];");
		for (Map.Entry<Integer,Picker<T>> e : m_providers.entrySet())
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
		return out.toString();
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
