package ca.uqac.lif.synthia.sequence;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.cep.synthia.util.Constant;
import ca.uqac.lif.synthia.Picker;

public abstract class BehaviorTree<T> implements Picker<T> 
{
	@Override
	public abstract BehaviorTree<T> duplicate(boolean with_state);
	
	public static class Sequence<T> extends BehaviorTree<T>
	{
		/*@ non_null @*/ protected List<BehaviorTree<T>> m_children;
		
		protected int m_index;
		
		public Sequence()
		{
			super();
			m_children = new ArrayList<BehaviorTree<T>>();
			m_index = 0;
		}
		
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
		
		@SuppressWarnings("unchecked")
		public Sequence<T> add(/*@ non_null @*/ BehaviorTree<T> ... nodes)
		{
			for (BehaviorTree<T> tn : nodes)
			{
				m_children.add(tn);
			}
			return this;
		}
		
		@Override
		public void reset()
		{
			m_index = 0;
		}

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
	
	public static class Choice<T> extends BehaviorTree<T>
	{
		/*@ non_null @*/ protected List<ProbabilityChoice<T>> m_choices;
		
		protected int m_chosenIndex;
		
		/*@ non_null @*/ protected Picker<Float> m_floatPicker;
		
		public Choice(/*@ non_null @*/ Picker<Float> float_picker)
		{
			super();
			m_choices = new ArrayList<ProbabilityChoice<T>>();
			m_chosenIndex = -1;
			m_floatPicker = float_picker;
		}
		
		@Override
		public void reset()
		{
			m_chosenIndex = -1;
			for (ProbabilityChoice<T> pc : m_choices)
			{
				pc.reset();
			}
		}
		
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
			return m_choices.get(m_chosenIndex).getNode().pick();
		}

		@Override
		public Choice<T> duplicate(boolean with_state)
		{
			Choice<T> ch = new Choice<T>(m_floatPicker.duplicate(with_state));
			for (ProbabilityChoice<T> pc : m_choices)
			{
				ch.m_choices.add(pc.duplicate(with_state));
			}
			if (with_state)
			{
				ch.m_chosenIndex = m_chosenIndex;
			}
			return ch;
		}
		
		public Choice<T> add(BehaviorTree<T> node, Number probability)
		{
			m_choices.add(new ProbabilityChoice<T>(node, probability));
			return this;
		}
		
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
		
		protected static class ProbabilityChoice<T>
		{
			/**
			 * The probability of taking this choice
			 */
			protected float m_probability;
			
			/**
			 * The tree node corresponding to that choice
			 */
			/*@ non_null @*/ protected BehaviorTree<T> m_node;
			
			public ProbabilityChoice(/*@ non_null @*/ BehaviorTree<T> node, /*@ non_null @*/ Number probability)
			{
				super();
				m_node = node;
				m_probability = probability.floatValue();
			}
			
			/*@ pure @*/ public float getProbability()
			{
				return m_probability;
			}
			
			/*@ pure non_null @*/ public BehaviorTree<T> getNode()
			{
				return m_node;
			}
			
			public void reset()
			{
				m_node.reset();
			}
			
			@Override
			public String toString()
			{
				return m_node.toString() + " (" + m_probability + ")";
			}
			
			/*@ pure non_null @*/ public ProbabilityChoice<T> duplicate(boolean with_state)
			{
				return new ProbabilityChoice<T>(m_node.duplicate(with_state), m_probability);
			}
		}
	}
	
	public static class Leaf<T> extends BehaviorTree<T>
	{
		/*@ non_null @*/ protected Picker<T> m_picker;
		
		public Leaf(/*@ non_null @*/ Picker<T> picker)
		{
			super();
			m_picker = picker;
		}
		
		public Leaf(/*@ non_null @*/ T t)
		{
			super();
			m_picker = new Constant<T>(t);
		}

		@Override
		public void reset() 
		{
			m_picker.reset();
		}

		@Override
		public T pick() 
		{
			return m_picker.pick();
		}

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