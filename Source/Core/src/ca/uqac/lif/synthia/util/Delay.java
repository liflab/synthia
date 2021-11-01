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
package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.Picker;

/**
 * Picker that does not produce any value, but causes the thread to wait
 * for a moment on every call to {@link #pick()}.
 * @author Sylvain Hallé
 */
public class Delay implements Picker<Object>
{
	/**
	 * The picker deciding on the time to wait, in seconds.
	 */
	protected Picker<? extends Number> m_delay;
	
	/**
	 * Creates a new delay picker.
	 * @param delay The picker deciding on the time to wait, in seconds
	 */
	public Delay(Picker<? extends Number> delay)
	{
		super();
		m_delay = delay;
	}

	@Override
	public void reset()
	{
		m_delay.reset();
	}

	@Override
	public Object pick()
	{
		wait(m_delay.pick().floatValue());
		return null;
	}

	@Override
	public Delay duplicate(boolean with_state)
	{
		return new Delay(m_delay.duplicate(with_state));
	}
	
	/**
	 * Waits for some time.
	 * @param duration The time to wait, in seconds
	 */
	public static void wait(float duration)
	{
		try
		{
			Thread.sleep((long) (duration * 1000));
		}
		catch (InterruptedException e)
		{
			// Do nothing
		}
	}
}
