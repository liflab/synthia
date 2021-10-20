package ca.uqac.lif.synthia.util;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;

public class ComposeList<T> implements Picker<List<T>>
{
	/**
	 * The picker providing elements for the list.
	 */
	/*@ non_null @*/ protected Picker<T> m_elements;
	
	/**
	 * The picker deciding on the length of the list.
	 */
	/*@ non_null @*/ protected Picker<Integer> m_length;
	
	/**
	 * Creates a new instance of the picker.
	 * @param elements The picker providing elements for the list
	 * @param length  The picker deciding on the length of the list
	 */
	public ComposeList(Picker<T> elements, Picker<Integer> length)
	{
		super();
		m_elements = elements;
		m_length = length;
	}
	
	@Override
	public void reset()
	{
		m_length.reset();
		m_elements.reset();
	}

	@Override
	public List<T> pick()
	{
		int len = m_length.pick();
		List<T> list = new ArrayList<T>(len);
		for (int i = 0; i < len; i++)
		{
			list.add(m_elements.pick());
		}
		return list;
	}

	@Override
	public ComposeList<T> duplicate(boolean with_state)
	{
		return new ComposeList<T>(m_elements.duplicate(with_state), m_length.duplicate(with_state));
	}

}
