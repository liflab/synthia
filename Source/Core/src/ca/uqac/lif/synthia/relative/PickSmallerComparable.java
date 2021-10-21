package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.exception.PickerException;

public class PickSmallerComparable<T> extends PickIf<T> implements Shrinkable<T>
{
	/**
	 * The reference object that candidate values are compared to.
	 */
	protected Comparable<T> m_reference;
	
	@SuppressWarnings("unchecked")
	public PickSmallerComparable(Picker<T> source, T o)
	{
		super(source);
		if (!(o instanceof Comparable))
		{
			throw new PickerException("Expected a comparable object");
		}
		m_reference = (Comparable<T>) o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PickSmallerComparable<T> duplicate(boolean with_state)
	{
		PickSmallerComparable<T> ps = new PickSmallerComparable<T>(m_picker.duplicate(with_state), (T) m_reference);
		super.copyInto(this, with_state);
		if (with_state)
		{
			ps.m_maxIteration = m_maxIteration;
		}
		return ps;
	}

	@Override
	public PickSmallerComparable<T> shrink(T o)
	{
		PickSmallerComparable<T> ps = new PickSmallerComparable<T>(m_picker, (T) o);
		ps.m_maxIteration = m_maxIteration;
		return ps;
	}

	@Override
	protected boolean select(T element)
	{
		return m_reference.compareTo(element) > 0;
	}
}
