package ca.uqac.lif.synthia.collection;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * Given a list <i>L</i>, a list <i>L</i>' is "smaller" list if either:
 * <ul>
 * <li><i>L</i>' has strictly fewer elements than <i>L</i>, or</li>
 * <li><i>L</i>' has the same number of elements <i>n</i>, and there
 * exists an index <i>i</i> &leq; <i>n</i> such that <i>L</i>[<i>i</i>] &lt;
 * <i>L</i>'[<i>i</i>] and <i>L</i>[<i>j</i>] = <i>L</i>'[<i>j</i>] for
 * all <i>j</i> &lt; <i>i</i>
 * </ul>
 * @author sylvain
 *
 * @param <T>
 */
public class ComposeShrunkList<T> implements Picker<List<T>>, Shrinkable<List<T>>
{
	protected Shrinkable<T> m_originalPicker;
	
	protected Shrinkable<Integer> m_length;
	
	protected List<T> m_reference;
	
	protected Picker<Float> m_decision;
	
	public ComposeShrunkList(Shrinkable<T> element_picker, Shrinkable<Integer> length, List<T> reference, Picker<Float> decision)
	{
		super();
		m_originalPicker = element_picker;
		m_reference = reference;
		m_decision = decision;
		m_length = length;
	}
	
	@Override
	public Shrinkable<List<T>> shrink(List<T> list, Picker<Float> d)
	{
		return new ComposeShrunkList<T>(m_originalPicker, m_length, list, m_decision);
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> pick()
	{
		int ref_size = m_reference.size();
		if (m_decision.pick() < 0.5)
		{
			// Generate a shorter list
			int new_length = m_length.shrink(ref_size, m_decision).pick();
			List<T> new_list = new ArrayList<T>(new_length);
			for (int i = 0; i < new_length; i++)
			{
				new_list.add(m_originalPicker.pick());
			}
			return new_list;
		}
		// Generate a list with smaller elements
		int prefix_length = (int) (ref_size * m_decision.pick());
		List<T> new_list = new ArrayList<T>(ref_size);
		for (int i = 0; i < ref_size; i++)
		{
			if (i < prefix_length)
			{
				new_list.add(m_reference.get(i));
			}
			else if (i == prefix_length)
			{
				new_list.add(m_originalPicker.shrink(m_reference.get(i), m_decision).pick());
			}
			else
			{
				new_list.add(m_originalPicker.pick());
			}
		}
		return new_list;
	}

	@Override
	public Picker<List<T>> duplicate(boolean with_state)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shrinkable<List<T>> shrink(List<T> o)
	{
		return shrink(o, RandomFloat.instance);
	}
}
