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
package examples.quickcheck;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;

/**
 * Simple illustration of the shrinking process on randomly generated integers.
 * @ingroup Examples
 * @author Sylvain Hallé
 *
 */
public class Prime
{
	public static void main(String[] args)
	{
		Assert<Integer> a = new Assert<Integer>(new RandomInteger(0, Integer.MAX_VALUE), new RandomFloat()) {
			protected boolean evaluate(Integer x) {
				return !isPrime(x);
			}
		};
		if (!a.check())
		{
			System.out.println("Assertion is false");
			System.out.println(a.getInitial() + " is prime");
			System.out.println(a.getIterations().size() + " shrinking steps");
			System.out.println(a.getShrunk() + " is also prime");
		}
	}

	/**
	 * Checks if an integer is prime.
	 * @param n The integer
	 * @return {@code true} if the integer is prime, {@code false} otherwise
	 */
	public static boolean isPrime(int n)
	{
		if (n < 2)
		{
			return false;
		}
		for (int x = 2; x < n / x; x++)
		{
			if (n % x == 0)
			{
				return false;
			}
		}
		return true;
	}
}
