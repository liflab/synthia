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

public class RandomInteger extends RandomPicker<Integer>
{
	protected int m_min;
	
	protected int m_max;
	
	public RandomInteger(int min, int max)
	{
		super();
		setInterval(min, max);
	}
	
	public void setInterval(int min, int max)
	{
		m_min = min;
		m_max = max;
	}
	
	@Override
	public Integer pick() 
	{
		return m_random.nextInt(m_max - m_min) + m_min;
	}
	
	@Override
	public RandomInteger duplicate(boolean with_state)
	{
		return new RandomInteger(m_min, m_max);
	}
}