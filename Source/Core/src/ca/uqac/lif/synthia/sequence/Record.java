/*
    Synthia, a data structure generator
    Copyright (C) 2019-2021 Laboratoire d'informatique formelle
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

import ca.uqac.lif.petitpoucet.NodeFactory;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.ExplanationQueryable;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.explanation.NthSuccessiveOutput;

/**
 * Picker that records and returns the values produced by another picker.
 * This picker can be used in combination with {@link Playback} to 
 * reliably reproduce the behavior of another picker.
 * <pre>
 * RandomInteger ri = new RandomInteger(0, 10);
 * Record&lt;Integer&gt; rec = new Record&lt;Integer&gt;(ri);
 * System.out.println(rec.pick()); // 3
 * System.out.println(rec.pick()); // 1
 * System.out.println(rec.pick()); // 4
 * List&lt;Integer&gt; values = rec.getValues(); // [3, 1, 4]
 * </pre>
 * @param <T> The type of objects to pick
 * @ingroup API
 */
public class Record<T> implements Picker<T>, ExplanationQueryable
{
	/**
	 * The picker that generates the values
	 */
	/*@ non_null @*/ protected Picker<T> m_picker;
	
	/**
	 * The list of values produced by the picker so far
	 */
	/*@ non_null @*/ protected List<T> m_values;
	
	/**
	 * Creates a new Record picker
	 * @param picker The picker that generates the values
	 */
	public Record(/*@ non_null @*/ Picker<T> picker)
	{
		super();
		m_picker = picker;
		m_values = new ArrayList<>();
	}

	private Record(Picker<T> picker, List<T> values)
	{
		m_picker = picker;
		m_values = values;
	}

	/**
	 * Puts the Record picker back into its initial state. This means that the
	 * sequence of calls to {@link #pick()} will produce the same values
	 * as when the object was instantiated.
	 */
	@Override
	public void reset() 
	{
		m_picker.reset();
		m_values.clear();
	}


	/**
	 * Picks a value, records it and returns the value. Typically, this method is expected to return non-null
	 * objects; a <tt>null</tt> return value is used to signal that no more
	 * objects will be produced. That is, once this method returns
	 * <tt>null</tt>, it should normally return <tt>null</tt> on all subsequent
	 * calls.
	 * @return The value recorded from another picker.
	 */
	@Override
	public T pick()
	{
		T value = m_picker.pick();
		m_values.add(value);
		return value;
	}


	/**
	 * Creates a copy of the Record picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the Record picker
	 */
	@Override
	/*@ pure non_null @*/ public Record<T> duplicate(boolean with_state)
	{

		Record<T> copy = new Record<T>(m_picker.duplicate(with_state), new ArrayList<T>(m_values));
		if (!with_state)
		{
			copy.m_values.clear();
		}
		return copy;
	}
	
	/**
	 * Gets the number of values recorded by the picker so far
	 * @return The number of recorded values
	 */
	/*@ pure @*/ public int getCount()
	{
		return m_values.size();
	}
	
	/**
	 * Gets the list of values produced by the picker so far
	 * @return The list of values
	 */
	/*@ pure non_null @*/ public List<T> getValues()
	{
		return m_values;
	}
	
	@Override
	public PartNode getExplanation(Part p)
	{
		return getExplanation(p, NodeFactory.getFactory());
	}

	@Override
	public PartNode getExplanation(Part p, NodeFactory f)
	{
		PartNode root = f.getPartNode(p, this);
		int index = -1;
		Part head = p.head();
		if (head != null && head instanceof NthSuccessiveOutput)
		{
			index = ((NthSuccessiveOutput) head).getIndex();
		}
		if (index < 0 || index > m_values.size())
		{
			// Not a valid part, end there
			return root;
		}
		root.addChild(f.getPartNode(p, m_picker));
		return root;
	}
}