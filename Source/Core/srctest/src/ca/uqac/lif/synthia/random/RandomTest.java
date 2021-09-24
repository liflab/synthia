package ca.uqac.lif.synthia.random;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import ca.uqac.lif.synthia.random.generators.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomTest
{

	@Test
	public void duplicationInt()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			Random random = new Random(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random.nextInt();
			}
			Random random_copy = random.Duplicate();
			Assertions.assertEquals(random.nextInt(), random_copy.nextInt());
		}

	}

	@Test
	public void duplicationFloat()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			Random random = new Random(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random.nextFloat();
			}
			Random random_copy = random.Duplicate();
			Assertions.assertEquals(random.nextFloat(), random_copy.nextFloat());
		}

	}

	@Test
	public void duplicationBoolean()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			Random random = new Random(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random.nextBoolean();
			}
			Random random_copy = random.Duplicate();
			Assertions.assertEquals(random.nextBoolean(), random_copy.nextBoolean());
		}
	}

	@Test
	public void duplicationGaussian()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			Random random = new Random(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random.nextGaussian();
			}
			Random random_copy = random.Duplicate();
			Assertions.assertEquals(random.nextGaussian(), random_copy.nextGaussian());
		}
	}
}
