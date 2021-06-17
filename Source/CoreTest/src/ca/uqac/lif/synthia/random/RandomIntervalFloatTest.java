package ca.uqac.lif.synthia.random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomIntervalFloatTest
{
	@Test
	public void sameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger randomInteger = new RandomInteger(0, 1000000);
			int randomSeed = randomInteger.pick();
			RandomFloat randomFloat = new RandomFloat();
			randomFloat.setSeed(randomSeed);
			float randomFloatVal = randomFloat.pick();
			int randomIntVal = randomInteger.pick();
			RandomIntervalFloat intervalFloat = new RandomIntervalFloat(0, randomIntVal * randomFloatVal);
			RandomIntervalFloat intervalFloat1 = new RandomIntervalFloat(0,
					randomIntVal * randomFloatVal);
			intervalFloat.setSeed(randomSeed);
			intervalFloat1.setSeed(randomSeed);
			for (int j = 0; j < 10000; j++)
			{
				Assertions.assertEquals(intervalFloat.pick(), intervalFloat1.pick());
			}
		}
	}

	@Test
	public void interval()
	{
		int min = 0;
		int max = 25;
		RandomIntervalFloat random_interval_float = new RandomIntervalFloat(min, max);
		for (int i = 0; i < 10000; i++)
		{
			float random_val = random_interval_float.pick();
			Assertions.assertTrue(min <= random_val && random_val <= max);
		}
	}

	@Test
	public void duplicateWithState()
	{
		int min = 1;
		int max = 25;
		for (int i = 0; i < 10000; i++)
		{
			RandomIntervalFloat random_interval_float = new RandomIntervalFloat(min, max);
			for (int j = 0; j < 10000; j++)
			{
				random_interval_float.pick();
			}
			RandomIntervalFloat random_interval_float_copy = random_interval_float.duplicate(true);
			Assertions.assertEquals(random_interval_float.pick(), random_interval_float_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		int min = 1;
		int max = 25;
		for (int i = 0; i < 10000; i++)
		{
			RandomIntervalFloat random_interval_float = new RandomIntervalFloat(min, max);
			for (int j = 0; j < 10000; j++)
			{
				random_interval_float.pick();
			}
			RandomIntervalFloat random_interval_float_copy = random_interval_float.duplicate(false);
			Assertions.assertNotEquals(random_interval_float.pick(), random_interval_float_copy.pick());
			random_interval_float.reset();
			random_interval_float_copy.reset();
			Assertions.assertEquals(random_interval_float.pick(), random_interval_float_copy.pick());
		}
	}
}
