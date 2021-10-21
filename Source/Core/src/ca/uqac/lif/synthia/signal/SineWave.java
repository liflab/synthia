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

public class SineWave extends PeriodicSignal
{
	public SineWave(/*@ non_null @*/ Picker<? extends Number> amplitude, /*@ non_null @*/ Picker<? extends Number> frequency, /*@ non_null @*/ Picker<? extends Number> phase)
	{
		super(amplitude, frequency, phase);
	}

	@Override
	public Float pick()
	{
		return m_amplitude.pick().floatValue() * (float) Math.sin(m_frequency.pick().floatValue() + m_phase.pick().floatValue());
	}

	@Override
	public SineWave duplicate(boolean with_state)
	{
		SineWave s = new SineWave(m_amplitude.duplicate(with_state), m_frequency.duplicate(with_state), m_phase.duplicate(with_state));
		super.copyInto(s, with_state);
		return s;
	}
}
