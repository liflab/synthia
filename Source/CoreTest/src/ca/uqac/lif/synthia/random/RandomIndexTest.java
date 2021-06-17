package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIndexTest
{
	@Test
  public void sameValuesSameSeed()
	{

		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_seed_gen = new RandomInteger(0, 1000000);
			RandomIndex random_index = new RandomIndex(0, 1000);
			RandomIndex random_index1 = new RandomIndex(0, 1000);

			int randomSeed = random_seed_gen.pick();

			random_index.setSeed(randomSeed);
			random_index1.setSeed(randomSeed);

			for (int j = 0; j < 10000; j++)
			{
				Assertions.assertEquals(random_index.pick(), random_index1.pick());
			}
		}

	}

	@Test
  public void interval1()
	{
		int min = 0;
		int max = 25;
		RandomIndex random_index = new RandomIndex(min, max);
		for (int i = 0; i < 10000; i++)
		{
			int random_val = random_index.pick();
			Assertions.assertTrue(min <= random_val && random_val <= max);
		}
	}

	@Test
  public void interval2()
	{
		RandomIndex random_index = new RandomIndex();
		for (int i = 0; i < 10000; i++)
		{
			int random_val = random_index.pick();
			Assertions.assertTrue(0 <= random_val && random_val <= 1);
		}
	}

	@Test
  public void duplicateWithState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomIndex random_index = new RandomIndex();
			for (int j = 0; j < 10000; j++)
			{
          random_index.pick();
			}
			RandomIndex random_index_copy = random_index.duplicate(true);
			Assertions.assertEquals(random_index.pick(), random_index_copy.pick());
		}
	}

	@Test
  public void duplicateWithoutState()
	{
		for (int i = 0; i < 1; i++)
		{
			RandomIndex random_index = new RandomIndex(0, 100);
			for (int j = 0; j < 10000; j++)
			{
          random_index.pick();
			}
			RandomIndex random_index_copy = random_index.duplicate(false);
			Assertions.assertNotEquals(random_index.pick(), random_index_copy.pick());
			random_index.reset();
			random_index_copy.reset();
			Assertions.assertEquals(random_index.pick(), random_index_copy.pick());
		}
	}
}