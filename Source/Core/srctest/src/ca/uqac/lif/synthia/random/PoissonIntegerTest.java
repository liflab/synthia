package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PoissonIntegerTest
{
	@Test
	public void sameValuesSameSeed()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int randomSeed = int_list.get(i);
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger small_poisson_integer1 = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			PoissonInteger big_poisson_integer1 = new PoissonInteger(1000);
			small_poisson_integer.setSeed(randomSeed);
			small_poisson_integer1.setSeed(randomSeed);
			big_poisson_integer.setSeed(randomSeed);
			big_poisson_integer1.setSeed(randomSeed);
			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(small_poisson_integer.pick(), small_poisson_integer1.pick());
				Assertions.assertEquals(big_poisson_integer.pick(), big_poisson_integer1.pick());
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
			int randomSeed = int_list.get(i);
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			small_poisson_integer.setSeed(randomSeed);
			big_poisson_integer.setSeed(randomSeed);
			for (int j = 0; j < 100; j++)
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
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int randomSeed = int_list.get(i);
			PoissonInteger small_poisson_integer = new PoissonInteger(25);
			PoissonInteger big_poisson_integer = new PoissonInteger(1000);
			small_poisson_integer.setSeed(randomSeed);
			big_poisson_integer.setSeed(randomSeed);
			for (int j = 0; j < 100; j++)
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