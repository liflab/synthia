package ca.uqac.lif.synthia.relative;

import java.util.Comparator;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;

public class PickSmallerComparator<T> extends PickIf<T>
{
	/**
	 * A comparator for objects of type T.
	 */
	protected Comparator<T> m_comparator;
	
	/**
	 * The reference object that candidate values are compared to.
	 */
	protected T m_reference;
	
	public PickSmallerComparator(Picker<T> source, T o, Comparator<T> c)
	{
		super(source);
		m_reference = o;
		m_comparator = c;
	}

	@Override
	public PickSmallerComparator<T> duplicate(boolean with_state)
	{
		PickSmallerComparator<T> ps = new PickSmallerComparator<T>(m_picker.duplicate(with_state), m_reference, m_comparator);
		if (with_state)
		{
			ps.m_maxIteration = m_maxIteration;
		}
		return ps;
	}

	public PickSmallerComparator<T> getSmaller(T o, Comparator<T> c)
	{
		PickSmallerComparator<T> ps = new PickSmallerComparator<T>(m_picker, o, c);
		ps.m_maxIteration = m_maxIteration;
		return ps;
	}

	@Override
	protected boolean select(T element)
	{
		return m_comparator.compare(element, m_reference) < 0;
	}
}
