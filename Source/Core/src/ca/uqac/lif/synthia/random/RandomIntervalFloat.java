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

/**
 * Picks a floating point number uniformly in an interval
 */
public class RandomIntervalFloat extends RandomPicker<Float>
{
	/**
	 * The lower bound of the interval
	 */
	protected float m_min;
	
	/**
	 * The higher bound of the interval
	 */
	protected float m_max;
	
	/**
	 * Creates a new instance of the picker.
	 * @param min The lower bound of the interval
	 * @param max The higher bound of the interval
	 */
	public RandomIntervalFloat(Number min, Number max)
	{
		super();
		m_min = min.floatValue();
		m_max = max.floatValue();
	}
	
	@Override
	public Float pick() 
	{
		return m_random.nextFloat() * (m_max - m_min) + m_min;
	}
	
	@Override
	public RandomIntervalFloat duplicate(boolean with_state)
	{
		return new RandomIntervalFloat(m_min, m_max);
	}
}