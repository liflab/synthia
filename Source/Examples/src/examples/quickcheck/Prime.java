package examples.quickcheck;

import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.random.RandomInteger;

/**
 * @ingroup Examples
 * @author sylvain
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
