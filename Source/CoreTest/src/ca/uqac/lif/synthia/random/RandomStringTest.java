package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RandomStringTest
{

	@Test
	public void newRandomStringHelloWorld()
	{
		RandomString random_string = new RandomString(10);
		for (int i = 0; i < 10; i++)
		{
			System.out.println(random_string.pick());
		}
	}

	//TODO rewrite the test when RandomString will implement Seedable
	@Test
	public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_seed_gen = new RandomInteger(0, 10);
			int randomSeed = random_seed_gen.pick();
			RandomInteger random_size_gen_string = new RandomInteger(0, 10);
			RandomInteger random_size_gen_string1 = new RandomInteger(0, 10);
			random_size_gen_string.setSeed(randomSeed);
			random_size_gen_string1.setSeed(randomSeed);
			RandomString random_string = new RandomString(random_size_gen_string.pick());
			RandomString random_string1 = new RandomString(random_size_gen_string1.pick());
			String test = random_string.pick();
			String test1 = random_string1.pick();
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
		for (int i = 0; i < 10000; i++)
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
		for (int i = 0; i < 10000; i++)
		{
			String random_val = random_string.pick();
			Assertions.assertEquals(random_string_lenght, random_val.length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		for (int i = 0; i < 10000; i++)
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
		for (int i = 0; i < 10000; i++)
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
