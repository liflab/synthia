package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RandomStringTest
{

	@Test
	public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_seed_gen = new RandomInteger(0, 1000);
			int randomSeed = random_seed_gen.pick();

			RandomString random_string = new RandomString(10);
			RandomString random_string1 = new RandomString(10);

			random_string.setSeed(randomSeed);
			random_string1.setSeed(randomSeed);

			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(random_string.pick(), random_string1.pick());
			}

		}
	}

	@Test
	public void interval()
	{
		int min = 1;
		int max = 25;
		RandomInteger random_integer = new RandomInteger(min, max);
		RandomString random_string = new RandomString(random_integer);
		for (int i = 0; i < 100; i++)
		{
			String random_val = random_string.pick();
			Assertions.assertTrue(min <= random_val.length() && random_val.length() <= max);
		}
	}

	@Test
	public void lenght()
	{
		int min = 1;
		int max = 25;
		RandomInteger random_integer = new RandomInteger(min, max);
		int random_string_lenght = random_integer.pick();
		RandomString random_string = new RandomString(random_string_lenght);
		for (int i = 0; i < 100; i++)
		{
			String random_val = random_string.pick();
			Assertions.assertEquals(random_string_lenght, random_val.length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		for (int i = 0; i < 10; i++)
		{
			RandomString random_string = new RandomString(100);
			for (int j = 0; j < 100; j++)
			{
				random_string.pick();
			}
			RandomString random_string_copy = (RandomString) random_string.duplicate(true);
			Assertions.assertEquals(random_string.pick(), random_string_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		for (int i = 0; i < 10; i++)
		{
			RandomString random_string = new RandomString(100);
			for (int j = 0; j < 100; j++)
			{
				random_string.pick();
			}
			RandomString random_string_copy = (RandomString) random_string.duplicate(false);
			Assertions.assertNotEquals(random_string.pick(), random_string_copy.pick());
			random_string.reset();
			random_string_copy.reset();
			Assertions.assertEquals(random_string.pick(), random_string_copy.pick());
		}
	}
}
