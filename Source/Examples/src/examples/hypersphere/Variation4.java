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
package examples.hypersphere;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.util.Choice;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Tick;
import ca.uqac.lif.synthia.vector.HyperspherePicker;

/**
 * Generates two-dimensional points lying along an oblique line.
 * The points are generated by composing pickers as in the following diagram:
 * <p>
 * <img src="{@docRoot}/doc-files/hypersphere/Variation4.png" alt="Diagram"> 
 * <p>
 * The angle for each point is fixed to 3&pi;/2, and the radius is picked
 * according to a normal distribution, resulting in points clustered close to
 * the origin.
 * 
 * @ingroup Examples
 */
public class Variation4 
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		RandomInteger ri = new RandomInteger(0, 2).setSeed(42);
		Constant<Float> c1 = new Constant<Float>(1 / 6f);
		Tick radius = new Tick(ri, c1);
		RandomFloat rf = new RandomFloat().setSeed(42);
		Choice<Double> start = new Choice<Double>(rf)
				.add(0d, 0.25).add(Math.PI / 2, 0.25).add(Math.PI, 0.25).add(3 * Math.PI / 2, 0.25);
		Constant<Float> c2 = new Constant<Float>(1 / 6f);
		Tick angle = new Tick(start, c2);
		HyperspherePicker hp = new HyperspherePicker(radius, angle);
		for (int i = 0; i < 100; i++)
		{
			System.out.println(Variations.printPoint(hp.pick()));
		}
	}

}