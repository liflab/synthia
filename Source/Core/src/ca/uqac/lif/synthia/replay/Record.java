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
package ca.uqac.lif.synthia.replay;

import java.util.ArrayList;
import java.util.List;

import ca.uqac.lif.synthia.Picker;

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
 */
public class Record<T> implements Picker<T>
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
		m_values = new ArrayList<T>();
	}

	@Override
	public void reset() 
	{
		m_picker.reset();
		m_values.clear();
	}

	@Override
	public T pick() 
	{
		T value = m_picker.pick();
		m_values.add(value);
		return value;
	}

	@Override
	/*@ pure non_null @*/ public Picker<T> duplicate(boolean with_state) 
	{
		// TODO Auto-generated method stub
		return null;
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
}
