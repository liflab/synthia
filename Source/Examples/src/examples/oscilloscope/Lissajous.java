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
package examples.oscilloscope;

import ca.uqac.lif.synthia.signal.SineWave;
import ca.uqac.lif.synthia.util.ComposeList;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Tick;
import ca.uqac.lif.synthia.vector.PrismPicker;

/**
 * Uses the {@link SineWave} picker with the {@link PrismPicker} to generate
 * <a href="https://en.wikipedia.org/wiki/Lissajous_curve">Lissajous figures</a>
 * on an oscilloscope screen.
 * <p>
 * The wiring diagram of pickers in this program is given below:
 * <p>
 * <img src="{@docRoot}/doc-files/oscilloscope/Lissajous.png" alt="Wiring diagram" />
 * <p>
 * We recall that a {@link SineWave} picker produces an output that corresponds
 * to the equation <i>A</i> sin(&omega;<i>t</i>+&phi;), where <i>A</i>, &omega;
 * and &phi; are pickers corresponding to amplitude, frequency and phase,
 * respectively. The program depends on two parameters:
 * <ul>
 * <li>the frequency ratio &omega;<sub>2</sub>/&omega;<sub>1</sub> between the
 * two sine wave pickers, noted <i>m</i></li>
 * <li>the phase difference &omega;<sub>2</sub>(&phi;<sub>2</sub> &minus;
 * &phi;<sub>1</sub>), where the term &phi;<sub>2</sub> &minus;
 * &phi;<sub>1</sub> is noted <i>n</i>
 * </ul>
 * <p>The program picks 1,000 points, and then plots them in a window that
 * simulates the operation of an {@link Oscilloscope}. 
 * You can run the program by changing these values to produce various figures.
 * Here are some classical examples:
 * <p>
 * <table cellpadding="5">
 * <tr>
 * <th>Figure</th>
 * <td><img src="{@docRoot}/doc-files/oscilloscope/Lissajous_r_1_p_0.png" alt="Lissajous figure" /></td>
 * <td><img src="{@docRoot}/doc-files/oscilloscope/Lissajous_r_2_p_5_pi_8.png" alt="Lissajous figure" /></td>
 * <td><img src="{@docRoot}/doc-files/oscilloscope/Lissajous_r_6_5_p_pi_4.png" alt="Lissajous figure" /></td>
 * </tr>
 * <tr>
 * <th>Parameters</th>
 * <td>m = 1, n = 0</td>
 * <td>m = 2, n = 5&pi;/8</td>
 * <td>m = 6/5, n = &pi;/4</td>
 * </tr>
 * </table>
 * <p><strong>Caution:</strong> pay attention when entering ratios such as 4/3;
 * you must make sure they are interpreted as a {@link double}; hence you must
 * write <tt>4d/3</tt> (the <tt>d</tt> forces a cast to <tt>double</tt>).
 * 
 * @ingroup Examples
 */
public class Lissajous
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		/* The frequency ratio between the two sine wave pickers. */
		double ratio = 2;
		
		/* The phase difference between the two sine wave pickers. Since many
		 * interesting Lissajous figures have their phase expressed in multiples of
		 * frequency, the phase will multiplied by the frequency in the picker
		 * below. */
		double phase = 5 * Math.PI / 8d;
		
		// Generator for x-coordinate
		SineWave x = new SineWave(
				new Constant<Number>(1), // amplitude 
				new Tick(new Constant<Number>(0), new Constant<Number>(1)), // frequency
				new Constant<Number>(0) // phase
		);
		// Generator for y-coordinate
		SineWave y = new SineWave(
				new Constant<Number>(1), // amplitude 
				new Tick(new Constant<Number>(0), new Constant<Number>(ratio)), // frequency
				new Constant<Number>(ratio * phase) // phase
		);
		// Create a list of 10000 points out of x and y values
		PrismPicker points = new PrismPicker(x, y);
		ComposeList<float[]> set = new ComposeList<float[]>(points, 1000);
		// Display these points in our "oscilloscope"
		Oscilloscope o = new Oscilloscope().addPoints(set.pick());
		o.setVisible(true);
	}
}
