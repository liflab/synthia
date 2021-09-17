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

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Tick;

/**
 * Return the value picked by a picker a defined number of times.
 * @author Sylvain Hallé
 */
public class BoundedPicker<T> implements Picker<T>
{
	/**
	 * A tick picker user to count the number of remaining objects the picker can produce.
	 */
	protected Tick m_outputsLeft;

	/**
	 * The source of values.
	 */
	/*@ non_null @*/ protected Picker<T> m_provider;

	/**
	 * A flag to check if the picker can still produce at least an another object.
	 */
	protected boolean m_isDone;

	/**
	 * Private constructor used for the duplication of the picker.
	 * @param provider The source of values.
	 * @param outputs_left The picker used to determine the number of remaining objects to produce.
	 * @param is_done A flag to check if the picker has finished to produce objects.
	 */
	private BoundedPicker(/*@ non_null @*/ Picker<T> provider, Tick outputs_left, boolean is_done)
	{
		super();
		m_provider = provider;
		m_outputsLeft = outputs_left;
		m_isDone = is_done;
	}

	/**
	 * Constructor using an integer to define the number of ouputs to produce.
	 * @param provider The picker used to pick the values returned by the bounded picker.
	 * @param length A defined number of outputs to produce
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, int length)
	{
		super();
		m_provider = provider;
		m_outputsLeft = new Tick(length, -1);
		m_isDone = false;
	}

	/**
	 * Constructor using a picker to define the number of ouputs to produce.
	 * @param provider The picker used to pick the values returned by the bounded picker.
	 * @param int_source The picker used to select the number of outputs to produce.
	 */
	public BoundedPicker(/*@ non_null @*/ Picker<T> provider, Picker<Integer> int_source)
	{
		m_provider = provider;
		m_outputsLeft = new Tick(int_source.pick(), -1);
		m_isDone = false;
	}

	@Override
	public void reset()
	{
		m_provider.reset();
		m_outputsLeft.reset();
		m_isDone = false;
	}

	/**
	 * Signals if the bounded picker picked all objects from m_provider.
	 * Loop attributes must be false. If loop is true, the method will always return false.
	 * @return true if the picker picked all objects from m_provider and false if it's not the case.
	 */
	public boolean isDone()
	{
		return m_isDone;
	}

	@Override
	public T pick()
	{
		int outputs_left = m_outputsLeft.pick().intValue();
		if (outputs_left >= 0)
		{
			if (outputs_left == 0)
			{
				m_isDone = true;
			}
			return m_provider.pick();
		}

		throw new NoMoreElementException();
	}

	/**
	 * Creates a copy of the bounded picker.
	 * @param with_state If set to <tt>false</tt>, the returned copy is set to
	 * the class' initial state (i.e. same thing as calling the picker's
	 * constructor). If set to <tt>true</tt>, the returned copy is put into the
	 * same internal state as the object it is copied from.
	 * @return The copy of the bounded picker
	 */
	@Override
	/*@ non_null @*/ public BoundedPicker<T> duplicate(boolean with_state)
	{
		BoundedPicker<T> copy = new BoundedPicker<T>(m_provider.duplicate(with_state),
				m_outputsLeft.duplicate(with_state), m_isDone);

		if (!with_state)
		{
			copy.reset();
		}

		return copy;
	}
}
