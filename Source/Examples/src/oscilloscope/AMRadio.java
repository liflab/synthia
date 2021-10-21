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

public class AMRadio
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		// Generator for x-coordinate
		Picker<Number> x = new Tick(-1, 0.01);
		// The signal to modulate
		Picker<Number> signal = new Tick(0, 0.001);
		// Generator for y-coordinate
		SineWave y = new SineWave(
				signal, // amplitude 
				new Tick(0, 0.5), // frequency
				new Constant<Number>(0) // phase
		);
		// Create a list of 500 points out of x and y values
		PrismPicker points = new PrismPicker(x, y);
		ComposeList<float[]> set = new ComposeList<float[]>(points, 500);
		// Display these points in our "oscilloscope"
		Oscilloscope o = new Oscilloscope().addPoints(set.pick());
		o.setVisible(true);
	}
}
