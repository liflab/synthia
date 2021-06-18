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
package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.Picker;

/**
 * Return the value picked by a picker a defined number of times.
 */
public class BoundedPicker<T> implements Picker<T>
{
	protected Picker<Integer> m_intSource;

	/**
	 * The source of values.
	 */
	/*@ non_null @*/ protected Picker<T> m_provider;

	/**
	 * The number of outputs remaining before the picker starts to return <tt>null</tt>.
	 */
	protected int m_outputsLeft;

	/**
	 * The number of ouputs to produce. It's equal to the initial number of outputs remaining.
	 */
	protected int m_outputsToDo;

	/**
	 * Constructor using an integer (lenght parameter) to define the number of ouputs to produce.
	 * @param provider The picker used to pick the values returned by the bounded picker.
	 * @param int_source A picker that can be used to select the number of outputs to produce.
	 * @param length A defined number of outputs to produce
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, Picker<Integer> int_source, int length)
	{
		super();
		m_provider = provider;
		m_intSource = int_source;
		m_outputsLeft = length;
		m_outputsToDo = length;
	}

	/**
	 * Constructor using a picker to define the number of ouputs to produce.
	 * @param provider The picker used to pick the values returned by the bounded picker.
	 * @param int_source The picker used to select the number of outputs to produce.
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, Picker<Integer> int_source)
	{
		int length = int_source.pick();
		m_provider = provider;
		m_intSource = int_source;
		m_outputsLeft = length;
		m_outputsToDo = length;
	}

	@Override
	public void reset()
	{
		m_provider.reset();
		m_intSource.reset();
		m_outputsLeft = m_outputsToDo;
	}

	@Override
	public T pick()
	{
		if (m_outputsLeft <= 0)
		{
			return null;
		}
		m_outputsLeft--;
		return m_provider.pick();
	}

	@Override
	/*@ non_null @*/ public BoundedPicker<T> duplicate(boolean with_state)
	{
		BoundedPicker<T> bp = new BoundedPicker<T>(m_provider.duplicate(with_state),
				m_intSource.duplicate(with_state), m_outputsToDo);

		if (with_state)
		{
			bp.m_outputsLeft = this.m_outputsLeft;
		}

		return bp;
	}
}
