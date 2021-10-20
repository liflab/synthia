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
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Choice.ProbabilityChoice;
import ca.uqac.lif.synthia.util.Once;

/**
 * Generates a sequence of objects by following a behavior tree.
 * A <a href="https://en.wikipedia.org/wiki/Behavior_tree_(artificial_intelligence,_robotics_and_control)">behavior
 * tree</a> is a structure whose leaf nodes are associated to {@link Picker}s, and whose
 * non-leaf nodes can be of two types:
 * <ul>
 * <li>{@link Sequence}: a sequence node calls {@link Picker#pick()} on its
 * first child until it returns <tt>null</tt>, then calls {@link Picker#pick()} on
 * its second child, and so on. When all children are exhausted, it returns
 * <tt>null</tt>.</li>
 * <li>{@link Selector}: a selector node chooses one of its children, and
 * calls {@link Picker#pick()} on it until it returns <tt>null</tt>.</li>
 * </ul>
 * 
 * @param <T> The type of objects returned by the behavior tree. The pickers
 * associated to each leaf node must return objects of type <tt>T</tt> or its
 * descendants.
 */
public abstract class BehaviorTree<T> implements Picker<T> 
{
	@Override
	public abstract BehaviorTree<T> duplicate(boolean with_state);
	
	/**
	 * Sequence node in a behavior tree
	 * @param <T> The type of objects returned by the behavior tree
	 */
	public static class Sequence<T> extends BehaviorTree<T>
	{
		/**
		 * The list of children to this node
		 */
		/*@ non_null @*/ protected List<BehaviorTree<T>> m_children;
		
		/**
		 * The index of the current child node that is being "executed"
		 * (i.e. on which calls to {@link Picker#pick()} are currently
		 * being made)
		 */
		protected int m_index;

		/**
		 * Creates a new sequence node with no children
		 */
		public Sequence()
		{
			super();
			m_children = new ArrayList<BehaviorTree<T>>();
			m_index = 0;
		}
		
		/**
		 * Creates a new sequence node with a list of child nodes
		 * @param nodes The child nodes
		 */
		@SuppressWarnings("unchecked")
		public Sequence(/*@ non_null @*/ BehaviorTree<T> ... nodes)
		{
			super();
			m_children = new ArrayList<BehaviorTree<T>>(nodes.length);
			for (BehaviorTree<T> n : nodes)
			{
				m_children.add(n);
			}
			m_index = 0;
		}
		
		/**
		 * Adds children to this nodes
		 * @param nodes The nodes to add as children
		 * @return This node
		 */
		@SuppressWarnings("unchecked")
		public Sequence<T> add(/*@ non_null @*/ BehaviorTree<T> ... nodes)
		{
			for (BehaviorTree<T> tn : nodes)
			{
				m_children.add(tn);
			}
			return this;
		}

		/**
		 * Puts the BehaviorTree back into its initial state. This means that the
		 * sequence of calls to {@link #pick()} will produce the same values
		 * as when the object was instantiated.
		 */
		@Override
		public void reset()
		{
			m_index = 0;
		}

		/**
		 * Picks a value from a child of the BehaviorTree. Typically, this method is expected
		 * to return non-null objects; a <tt>null</tt> return value is used to signal that no more
		 * objects will be produced. That is, once this method returns
		 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
		 * calls.
		 * @return The value from a child of the BehaviorTree.
		 */

		@Override
		public T pick() 
		{
			T t = null;
			while (t == null && m_index < m_children.size())
			{
				BehaviorTree<T> tn = m_children.get(m_index);
				t = tn.pick();
				if (t == null)
				{
					m_index++;
				}
			}
			return t;
		}

		/**
		 * Creates a copy of the BehaviorTree.
		 * @param with_state If set to <tt>false</tt>, the returned copy is set to
		 * the class' initial state (i.e. same thing as calling the picker's
		 * constructor). If set to <tt>true</tt>, the returned copy is put into the
		 * same internal state as the object it is copied from.
		 * @return The copy of the BehaviorTree
		 */
		@Override
		public Sequence<T> duplicate(boolean with_state)
		{
			Sequence<T> tn = new Sequence<T>();
			for (BehaviorTree<T> child : m_children)
			{
				tn.m_children.add(child.duplicate(with_state));
			}
			if (with_state)
			{
				tn.m_index = m_index;
			}
			return tn;
		}
	}
	
	/**
	 * Selector node in a behavior tree
	 * @param <T> The type of objects returned by the behavior tree
	 */
	public static class Selector<T> extends BehaviorTree<T>
	{
		/*@ non_null @*/ protected List<NodeProbabilityChoice<T>> m_choices;
		
		/**
		 * The index of the child node that is being "executed"
		 * (i.e. on which calls to {@link Picker#pick()} are currently
		 * being made). This field contains value -1 until a child node
		 * is chosen.
		 */
		protected int m_chosenIndex;
		
		/**
		 * A picker used to choose the child node
		 */
		/*@ non_null @*/ protected Picker<Float> m_floatPicker;
		
		/**
		 * Creates a new selector node with no children
		 * @param float_picker A picker used to choose the child node when
		 * a call to {@link #pick()} is being made
		 */
		public Selector(/*@ non_null @*/ Picker<Float> float_picker)
		{
			super();
			m_choices = new ArrayList<NodeProbabilityChoice<T>>();
			m_chosenIndex = -1;
			m_floatPicker = float_picker;
		}


		/**
		 * Puts the Selector back into its initial state. This means that the
		 * sequence of calls to {@link #pick()} will produce the same values
		 * as when the object was instantiated.
		 */
		@Override
		public void reset()
		{
			m_chosenIndex = -1;
			for (NodeProbabilityChoice<T> pc : m_choices)
			{
				pc.reset();
			}
		}


		/**
		 * Picks a value from a child of the Node. Typically, this method is expected to return non-null
		 * objects; a <tt>null</tt> return value is used to signal that no more
		 * objects will be produced. That is, once this method returns
		 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
		 * calls.
		 * @return The value picked from a child of the Selector Node.
		 */
		@Override
		public T pick() 
		{
			if (m_chosenIndex < 0)
			{
				float[] probs = new float[m_choices.size()];
				float total_prob = 0f;
				for (int i = 0; i < probs.length; i++)
				{
					total_prob += m_choices.get(i).getProbability();
					probs[i] = total_prob;
				}
				float f = m_floatPicker.pick();
				int index = 0;
				while (index < probs.length)
				{
					if (f <= probs[index])
					{
						m_chosenIndex = index;
						break;
					}
					index++;
				}
			}
			if (m_chosenIndex >= m_choices.size())
			{
				return null;
			}
			return m_choices.get(m_chosenIndex).getObject().pick();
		}

		/**
		 * Creates a copy of the Selector.
		 * @param with_state If set to <tt>false</tt>, the returned copy is set to
		 * the class' initial state (i.e. same thing as calling the picker's
		 * constructor). If set to <tt>true</tt>, the returned copy is put into the
		 * same internal state as the object it is copied from.
		 * @return The copy of the Selector
		 */
		@Override
		public Selector<T> duplicate(boolean with_state)
		{
			Selector<T> ch = new Selector<T>(m_floatPicker.duplicate(with_state));
			for (NodeProbabilityChoice<T> pc : m_choices)
			{
				ch.m_choices.add(pc.duplicate(with_state));
			}
			if (with_state)
			{
				ch.m_chosenIndex = m_chosenIndex;
			}
			return ch;
		}
		
		/**
		 * Adds a new child to this node
		 * @param node The behavior tree node to add
		 * @param probability The probability of this node being picked. The
		 * sum of probabilities associated to all child nodes of a given
		 * selector node should be equal to 1.
		 * @return This node
		 */
		public Selector<T> add(BehaviorTree<T> node, Number probability)
		{
			m_choices.add(new NodeProbabilityChoice<T>(node, probability));
			return this;
		}


		/**
		 * Returns the BehaviorTree Node as a string.
		 * @return The string representing the BehaviorTree Node.
		 */
		@Override
		public String toString()
		{
			StringBuilder out = new StringBuilder();
			int size = m_choices.size();
			String beg = "", end = "";
			if (size > 1)
			{
				beg = "(";
				end = ")";
			}
			out.append(beg);
			for (int i = 0; i < m_choices.size(); i++)
			{
				if (i > 0)
				{
					out.append(" || ");
				}
				out.append(m_choices.get(i).toString());
			}
			out.append(end);
			return out.toString();
		}
		
		/**
		 * Simple data structure asssociating a behavior tree node with
		 * a probability. This is an object used internally by
		 * {@link Selector} to handle child nodes with probabilities.
		 * @param <T> The type of objects returned by the behavior tree
		 */
		protected static class NodeProbabilityChoice<T> extends ProbabilityChoice<BehaviorTree<T>>
		{	
			/**
			 * Creates a new probability-node association
			 * @param t The object
			 * @param p The probability of picking this node
			 */
			public NodeProbabilityChoice(/*@ non_null @*/ BehaviorTree<T> t, /*@ non_null @*/ Number p)
			{
				super(t, p);
			}
			
			/**
			 * Resets the node
			 */
			public void reset()
			{
				m_object.reset();
			}
			
			/**
			 * Duplicates this probability-node association
			 * @param with_state If set to <tt>true</tt>, the node is duplicated
			 * with its state
			 * @return A duplicate of this association
			 */
			/*@ pure non_null @*/ public NodeProbabilityChoice<T> duplicate(boolean with_state)
			{
				return new NodeProbabilityChoice<T>(m_object.duplicate(with_state), m_probability);
			}
		}
	}
	
	/**
	 * Leaf node of a behavior tree. A leaf node contains an internal {@link Picker},
	 * which is called when the node's {@link #pick()} method is called.
	 * @param <T> The type of objects returned by the behavior tree
	 */
	public static class Leaf<T> extends BehaviorTree<T>
	{
		/**
		 * The internal picker that will be called on a call to
		 * the node's {@link #pick()} method
		 */
		/*@ non_null @*/ protected Picker<T> m_picker;
		
		/**
		 * Creates a new leaf node
		 * @param picker The internal picker that will be called on a call to
		 * the node's {@link #pick()} method
		 */
		public Leaf(/*@ non_null @*/ Picker<T> picker)
		{
			super();
			m_picker = picker;
		}
		
		/**
		 * Creates a new leaf node that outputs a single value exactly once
		 * @param t The value to output
		 */
		public Leaf(/*@ non_null @*/ T t)
		{
			super();
			m_picker = new Once<T>(new Constant<T>(t));
		}

		/**
		 * Puts the Leaf back into its initial state. This means that the
		 * sequence of calls to {@link #pick()} will produce the same values
		 * as when the object was instantiated.
		 */
		@Override
		public void reset() 
		{
			m_picker.reset();
		}
		
		/**
		 * Picks the value from the leaf node. Typically, this method is expected to return non-null
		 * objects; a <tt>null</tt> return value is used to signal that no more
		 * objects will be produced. That is, once this method returns
		 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
		 * calls.
		 * @return The value of the leaf node
		 */
		@Override
		public T pick() 
		{
			return m_picker.pick();
		}


		/**
		 * Creates a copy of the Leaf.
		 * @param with_state If set to <tt>false</tt>, the returned copy is set to
		 * the class' initial state (i.e. same thing as calling the picker's
		 * constructor). If set to <tt>true</tt>, the returned copy is put into the
		 * same internal state as the object it is copied from.
		 * @return The copy of the leaf node
		 */
		@Override
		public Leaf<T> duplicate(boolean with_state) 
		{
			return new Leaf<T>(m_picker.duplicate(with_state));
		}

		@Override
		public String toString()
		{
			return m_picker.toString();
		}
	}
}