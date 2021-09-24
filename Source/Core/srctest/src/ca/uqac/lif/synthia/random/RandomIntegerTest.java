package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomIntegerTest
{

	@Test public void sameValuesSameSeed()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			RandomInteger random_integer2 = new RandomInteger(0, 1000);
			int randomSeed = int_list.get(i);
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(random_integer1.pick(), random_integer2.pick());
			}
		}

	}

	@Test
	public void interval()
	{
		int min = 0;
		int max = 25;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_integer = new RandomInteger(min, max);
			random_integer.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				int random_val = random_integer.pick();
				Assertions.assertTrue(min <= random_val && random_val <= max);
			}
		}

	}

	@Test
	public void duplicateWithState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000);
			random_integer.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
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
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_integer = new RandomInteger(0,25);
			random_integer.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random_integer.pick();
			}
			RandomInteger random_integer_copy = random_integer.duplicate(false);


			int counter =0;
			for (int j = 0; j < 5; j++)
			{
				int x =random_integer.pick();
				int y= random_integer_copy.pick();
				if(x==y)
				{
					counter++;
				}
			}
			Assertions.assertNotEquals(5, counter);
			random_integer.reset();
			random_integer_copy.reset();
			for (int j = 0; j < 105; j++)
			{
				Assertions.assertEquals(random_integer.pick(), random_integer_copy.pick());
			}

		}
	}


}
