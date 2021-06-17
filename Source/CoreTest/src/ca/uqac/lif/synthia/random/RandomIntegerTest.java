package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIntegerTest
{

	@Test public void sameValuesSameSeed()
	{

		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_seed_gen = new RandomInteger(0, 1000000);
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			RandomInteger random_integer2 = new RandomInteger(0, 1000);
			int randomSeed = random_seed_gen.pick();
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			for (int j = 0; j < 10000; j++)
			{
				Assertions.assertEquals(random_integer1.pick(), random_integer2.pick());
			}
		}

	}

	@Test public void interval()
	{
		int min = 0;
		int max = 25;
		RandomInteger random_integer = new RandomInteger(min, max);
		for (int i = 0; i < 10000; i++)
		{
			int random_val = random_integer.pick();
			Assertions.assertTrue(min <= random_val && random_val <= max);
		}
	}

	@Test
	public void duplicateWithState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000);
			for (int j = 0; j < 10000; j++)
			{
				random_integer.pick();
			}
			RandomInteger random_integer_copy = random_integer.duplicate(true);
			Assertions.assertEquals(random_integer.pick(), random_integer_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		for (int i = 10000; i < 1; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000);
			for (int j = 0; j < 10000; j++)
			{
				random_integer.pick();
			}
			RandomInteger random_integer_copy = random_integer.duplicate(false);
			Assertions.assertNotEquals(random_integer.pick(), random_integer_copy.pick());
			random_integer.reset();
			random_integer_copy.reset();
			Assertions.assertEquals(random_integer.pick(), random_integer_copy.pick());
		}
	}

}
