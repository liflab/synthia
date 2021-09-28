package quickcheck;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import ca.uqac.lif.synthia.random.RandomInteger;
import ca.uqac.lif.synthia.relative.PickIfSmaller;

public class Prime
{
	public static void main(String[] args)
	{
		RandomInteger r_int = new RandomInteger(0, Integer.MAX_VALUE);
		int i;
		do {
			i = r_int.pick();
		} while (!isPrime(i));
		System.out.println(i + " is prime");
		PickIfSmaller<Integer> p_int = new PickIfSmaller<Integer>(r_int);
		try
		{
			do {
				do {
					i = p_int.pick();
				} while (!isPrime(i));
				System.out.println(i + " is also prime");
			} while (true);
		}
		catch (NoMoreElementException e)
		{
			// Nothing to do
		}
		System.out.println("Done");

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
