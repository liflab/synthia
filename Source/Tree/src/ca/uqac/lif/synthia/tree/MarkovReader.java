package ca.uqac.lif.synthia.tree;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.sequence.MarkovChain;

/**
 * Converts a graph into an equivalent Markov chain. Each vertex of the
 * graph is turned into a state of the Markov chain by overriding method
 * {@link #getPicker(Object) getPicker()}. Transitions in the chain mirror the
 * adjacency relation of the graph, assigning equal probability to each
 * outgoing edge.
 * 
 * @author Sylvain Hallé
 *
 * @param <T> The type of the nodes in the graph.
 * @param <U> The type of the nodes in the Markov chain.
 */
public class MarkovReader<T,U> extends GraphCrawler<T>
{
	/**
	 * Converts a graph into a {@linkplain MarkovChain Markov chain}.
	 * @param start The node of the graph that will be used as the initial
	 * state of the chain.
	 * @param float_source A source of floating-point numbers, used to
	 * instantiate the Markov chain.
	 * @return The resulting Markov chain
	 */
	public MarkovChain<U> asMarkovChain(Node<T> start, Picker<Float> float_source)
	{
		MarkovChain<U> markov = new MarkovChain<U>(float_source);
		Set<Node<T>> visited = new HashSet<Node<T>>();
		Queue<Node<T>> to_visit = new ArrayDeque<Node<T>>();
		to_visit.add(start);
		while (!to_visit.isEmpty())
		{
			Node<T> current = to_visit.remove();
			if (visited.contains(current))
			{
				continue;
			}
			visited.add(current);
			int id = getId(current);
			markov.add(id, getPicker(current.getLabel()));
			float degree = current.getChildren().size();
			for (Node<T> child : current.getChildren())
			{
				int t_id = getId(child);
				markov.add(id, t_id, 1f / degree);
				if (!visited.contains(child) && !to_visit.contains(child))
				{
					to_visit.add(child);
				}
			}
		}
		return markov;
	}
	
	protected Picker<U> getPicker(T t)
	{
		return null;
	}
}
