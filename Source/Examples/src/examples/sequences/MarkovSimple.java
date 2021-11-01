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
package examples.sequences;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.sequence.MarkovChain;
import ca.uqac.lif.synthia.sequence.Playback;
import ca.uqac.lif.synthia.string.StringPattern;
import ca.uqac.lif.synthia.util.Constant;
import ca.uqac.lif.synthia.util.Tick;

/**
 * Generates a sequence of values according to a Markov chain.
 * @ingroup Examples
 */
public class MarkovSimple
{
	public static void main(String[] args)
	{
		Tick tick = new Tick(0, new Playback<Integer>(new Integer[] {1, 2, 1}));
		MarkovChain<String> mc = new MarkovChain<String>(new RandomFloat());
		mc.add(0, new Constant<String>(""));
		mc.add(1, new Constant<String>("START"));
		mc.add(2, new StringPattern("{$0},A", tick));
		mc.add(3, new StringPattern("{$0},B", tick));
		mc.add(4, new StringPattern("{$0},C", tick));
		mc.add(0, 1, 1).add(1, 2, 0.9).add(1, 4, 0.1);
		mc.add(2, 3, 1).add(3, 2, 0.7).add(3, 4, 0.3).add(4, 4, 1);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(mc.pick());
		}
	}

}
