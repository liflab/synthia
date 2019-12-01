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
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomFloat;

/**
 * Generates a sequence of monotonically increasing numerical values.
 * The <tt>Tick</tt> picker requires two parameters:
 * <ul>
 * <li>A numerical picker indicating how the first value should be
 * produced</li>
 * <li>Another numerical picker indicating by how much to increment the
 * current value to produce the next one</li> 
 * </ul>
 * Consider the following piece of code:
 * <pre>
 * Tick t1 = new Tick(10, 0.5);
 * Tick t2 = new Tick(new RandomInteger(8, 12), new RandomIntervalFloat(0.5, 1.5));
 * </pre>
 * The first instruction creates a <tt>Tick</tt> picker whose initial value is
 * 10, and each subsequent value is incremented by exactly 0.5. The second
 * instruction creates a <tt>Tick</tt> picker whose initial value is picked in
 * the interval [8,12), and where each subsequent value is incremented by a
 * number randomly chosen between 0.5 and 1.5.
 */
public class Tick implements Picker<Number>
{
	/**
	 * Picker that determines the start value
	 */
	protected Picker<? extends Number> m_startValue;
	
	/**
	 * Variable holding the current value of the tick
	 */
	protected float m_currentValue;

	/**
	 * Picker that determines the increment for each
	 * subsequent value
	 */
	protected Picker<? extends Number> m_increment;
	
	/**
	 * Creates a new Tick picker.
	 * @param start A picker that determines the start value
	 * @param increment A picker that determines the increment for each
	 * subsequent value
	 */
	public Tick(Picker<? extends Number> start, Picker<? extends Number> increment)
	{
		super();
		m_startValue = start;
		m_increment = increment;
		m_currentValue = m_startValue.pick().floatValue();
	}
	
	/**
	 * Creates a new Tick picker.
	 * @param start The start value
	 * @param increment The increment added to each subsequent value
	 */
	public Tick(Number start, Number increment)
	{
		this(new Constant<Number>(start), new Constant<Number>(increment));
	}
	
	/**
	 * Creates a new Tick picker.
	 * @param start The start value
	 * @param increment A picker that determines the increment for each
	 * subsequent value
	 */
	public Tick(Number start, Picker<? extends Number> increment)
	{
		this(new Constant<Number>(start), increment);
	}
	
	/**
	 * Creates a new Tick picker starting at value 0.
	 * @param increment A picker that determines the increment for each
	 * subsequent value
	 */
	public Tick(Picker<? extends Number> increment)
	{
		this(new Constant<Number>(0), increment);
	}
	
	/**
	 * Creates a new Tick picker starting at value 0, and where each subsequent
	 * value is incremented by a number uniformly chosen in the interval [0,1].
	 */
	public Tick()
	{
		this(new Constant<Number>(0), new RandomFloat());
	}

	@Override
	public void reset()
	{
		m_startValue.reset();
		m_increment.reset();
		m_currentValue = m_startValue.pick().floatValue();
	}

	@Override
	public Number pick() 
	{
		m_currentValue += m_increment.pick().floatValue();
		return m_currentValue;
	}

	@Override
	public Tick duplicate(boolean with_state) 
	{
		Tick tp = new Tick(m_startValue, m_increment);
		if (with_state)
		{
			tp.m_currentValue = m_currentValue;
		}
		return tp;
	}
	
	/*@ pure non_null @*/ public Tick duplicate(float start_value)
	{
		return new Tick(start_value, m_increment);
	}
	
	/*@ non_null @*/ public Tick setValue(float value)
	{
		m_currentValue = value;
		m_startValue = new Constant<Float>(value);
		return this;
	}
}
