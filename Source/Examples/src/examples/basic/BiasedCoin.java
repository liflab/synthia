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
package examples.basic;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;

/**
 * Uses {@link RandomBoolean} to simulate a series of biased coin tosses.
 * The program shows the number of times the picker produces "heads"
 * (i.e. true) and compares this to the expected number of heads as
 * dictated by the probability. A possible run of the program is:
 * <pre>
 * Flips    Heads   Expected
 * 100      31      30
 * 200      58      60
 * 300      84      90
 * 400      119     120
 * 500      155     150
 * 600      188     180
 * 700      218     210
 * 800      251     240
 * 900      282     270
 * 1000     312     300
 * </pre>
 * @author Sylvain Hallé
 * @ingroup Examples
 */
public class BiasedCoin
{
	public static void main(String[] args)
	{
		/* The probability of obtaining true (i.e. heads). */
		float p = 0.3f;
		
		/* Create a Boolean picker with this probability, call its pick() method
		 * 1,000 times, and show how many times the value true has been observed
		 * every 100 events. */
		int num_heads = 0;
		System.out.println("Flips\tHeads\tExpected");
		Picker<Boolean> coin = new RandomBoolean(p).setSeed(0);
		for (int i = 1; i <= 10; i++)
		{
			for (int j = 1; j <= 100; j++)
				num_heads += coin.pick() ? 1 : 0;
			System.out.printf("%d\t%d\t%d\n", 100 * i, num_heads, 100 * i * p);
		}
	}
}
