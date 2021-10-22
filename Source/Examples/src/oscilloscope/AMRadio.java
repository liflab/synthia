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
package oscilloscope;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.signal.SineWave;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Tick;
import ca.uqac.lif.synthia.vector.PrismPicker;

/**
 * Illustrates the principle of
 * <a href="https://en.wikipedia.org/wiki/Amplitude_modulation">amplitude
 * modulation</a> using sine wave and prism pickers. In this example,
 * the signal to modulate and the carrier wave can be shown on the
 * {@link Oscilloscope} respectively as:
 * <p>
 * <img src="{@docRoot}/doc-files/oscilloscope/Signal.png" alt="Signal" />
 * <img src="{@docRoot}/doc-files/oscilloscope/AM_Carrier.png" alt="Carrier" />
 * <p>
 * The wiring diagram of pickers corresponding to frequency modulation is
 * as follows:
 * <p>
 * When running the program, the resulting modulated signal is shown on
 * the oscilloscope:
 * <p>
 * <img src="{@docRoot}/doc-files/oscilloscope/AM_Modulated.png" alt="Modulated signal" />
 * <p>
 * As one can see, the frequency of the signal does not change, but its
 * amplitude does; this corresponds to the varying height of each cycle of the
 * carrier.
 */
public class AMRadio
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		// Generator for x-coordinate
		Picker<Number> x = new Tick(-1, 0.0001);
		// The signal to modulate
		SineWave signal = new SineWave(
				new Constant<Number>(1),
				new Tick(0, 0.00075),
				new Constant<Number>(0)
				);
		// Generator for y-coordinate
		SineWave y = new SineWave(
				signal, // amplitude 
				new Tick(0, 0.01), // frequency
				new Constant<Number>(0) // phase
				);
		// Create a list of 500 points out of x and y values
		PrismPicker points = new PrismPicker(x, y);
		ComposeList<float[]> set = new ComposeList<float[]>(points, 20000);
		// Display these points in our "oscilloscope"
		Oscilloscope o = new Oscilloscope().addPoints(set.pick());
		o.setVisible(true);
	}
}
