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
package ca.uqac.lif.synthia.signal;

import ca.uqac.lif.synthia.Picker;

/**
 * A signal picker that is characterized by an amplitude and a phase.
 * @author Sylvain Hallé
 * @ingroup API
 */
public abstract class PeriodicSignal implements Picker<Number>
{
	/**
	 * A picker determining the amplitude of the signal.
	 */
	/*@ non_null @*/ protected Picker<? extends Number> m_amplitude;
	
	/**
	 * A picker determining the frequency of the signal.
	 */
	protected Picker<? extends Number> m_frequency;
	
	/**
	 * A picker determining the phase of the signal.
	 */
	protected Picker<? extends Number> m_phase;
	
	/**
	 * The current position in the signal.
	 */
	protected float m_currentPosition;
	
	/**
	 * Creates a new instance of the signal picker.
	 * @param amplitude A picker determining the amplitude of the signal
	 * @param amplitude A picker determining the angular frequency of the signal
	 * @param m_phase A picker determining the phase of the signal
	 */
	public PeriodicSignal(/*@ non_null @*/ Picker<? extends Number> amplitude, /*@ non_null @*/ Picker<? extends Number> frequency, /*@ non_null @*/ Picker<? extends Number> phase)
	{
		super();
		m_amplitude = amplitude;
		m_frequency = frequency;
		m_phase = phase;
		m_currentPosition = 0;
	}
	
	@Override
	public void reset()
	{
		m_amplitude.reset();
		m_phase.reset();
		m_currentPosition = 0;
	}
	
	/**
	 * Copies the content of a periodic signal picker into another.
	 * @param ps The picker to copy into
	 * @param with_state Whether the duplication is stateful
	 */
	protected void copyInto(PeriodicSignal ps, boolean with_state)
	{
		ps.m_amplitude = m_amplitude.duplicate(with_state);
		ps.m_phase = m_phase.duplicate(with_state);
		if (with_state)
		{
			ps.m_currentPosition = m_currentPosition;
		}
	}
}
