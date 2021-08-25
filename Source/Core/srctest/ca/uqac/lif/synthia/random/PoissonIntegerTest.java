package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PoissonIntegerTest
{
	@Test
	public void sameValuesSameSeed()
	{

		for (int i = 0; i < 1000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			int randomSeed = random_integer.pick();
			int randomSeed1 = random_integer.pick();
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger small_poisson_integer1 = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			PoissonInteger big_poisson_integer1 = new PoissonInteger(1000);
			small_poisson_integer.setSeed(randomSeed);
			small_poisson_integer1.setSeed(randomSeed);
			big_poisson_integer.setSeed(randomSeed1);
			big_poisson_integer1.setSeed(randomSeed1);
			for (int j = 0; j < 1000; j++)
			{
				Assertions.assertEquals(small_poisson_integer.pick(), small_poisson_integer1.pick());
				Assertions.assertEquals(big_poisson_integer.pick(), big_poisson_integer1.pick());
			}
		}
	}

	@Test
	public void duplicateWithState()
	{
		for (int i = 0; i < 1000; i++)
		{
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			for (int j = 0; j < 1000; j++)
			{
				small_poisson_integer.pick();
				big_poisson_integer.pick();
			}
			PoissonInteger small_poisson_integer_copy = small_poisson_integer.duplicate(true);
			PoissonInteger big_poisson_integer_copy	= big_poisson_integer.duplicate(true);
			Assertions.assertEquals(small_poisson_integer.pick(), small_poisson_integer_copy.pick());
			Assertions.assertEquals(big_poisson_integer.pick(), big_poisson_integer_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		for (int i = 0; i < 1000; i++)
		{
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			for (int j = 0; j < 1000; j++)
			{
				small_poisson_integer.pick();
				big_poisson_integer.pick();
			}
			PoissonInteger small_poisson_integer_copy = small_poisson_integer.duplicate(false);
			PoissonInteger big_poisson_integer_copy	= big_poisson_integer.duplicate(false);
			small_poisson_integer.reset();
			small_poisson_integer_copy.reset();
			big_poisson_integer.reset();
			big_poisson_integer_copy.reset();
			Assertions.assertEquals(small_poisson_integer.pick(), small_poisson_integer_copy.pick());
			Assertions.assertEquals(big_poisson_integer.pick(), big_poisson_integer_copy.pick());
		}
	}
}