package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomFloatTest
{
	@Test
	public void SameValuesSameSeed()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			random_float.setSeed(random_seed);
			random_float1.setSeed(random_seed);
			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(random_float.pick(), random_float1.pick());
			}
		}
	}

	@Test
	public void interval()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int j=0; j<10;j++)
		{
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(int_list.get(j));
			for (int i = 0; i < 100; i++)
			{
				float random_val = random_float.pick();
				Assertions.assertTrue(0 <= random_val && random_val <= 1);
			}
		}

	}

	@Test
	public void duplicateWithState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i=0; i<10;i++)
		{
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(int_list.get(i));
			for (int j = 0; j < 10; j++)
			{
				random_float.pick();
			}
			RandomFloat random_float_copy = random_float.duplicate(true);
			Assertions.assertEquals(random_float.pick(), random_float_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i=0; i<10;i++)
		{
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(int_list.get(i));
			for (int j = 0; j < 10; j++)
			{
				random_float.pick();
			}
			RandomFloat random_float_copy = random_float.duplicate(false);
			Assertions.assertNotEquals(random_float.pick(), random_float_copy.pick());
			random_float.reset();
			random_float_copy.reset();
			Assertions.assertEquals(random_float.pick(), random_float_copy.pick());

		}
	}
}
