package ca.uqac.lif.synthia.random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RandomFloatTest
{
	@Test
	public void SameValuesSameSeed()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomInteger random_seed_gen = new RandomInteger(0, 1000000);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			int random_seed = random_seed_gen.pick();
			random_float.setSeed(random_seed);
			random_float1.setSeed(random_seed);
			for (int j = 0; j < 10000; j++)
			{
				assertEquals(random_float.pick(), random_float1.pick());
			}
		}
	}

	@Test
	public void interval()
	{
		RandomFloat random_float = new RandomFloat();
		for (int i = 0; i < 10000; i++)
		{
			float random_val = random_float.pick();
			assertTrue(0 <= random_val && random_val <= 1);
		}
	}

	@Test
	public void duplicateWithState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomFloat random_float = new RandomFloat();
			for (int j = 0; j < 10; j++)
			{
				random_float.pick();
			}
			RandomFloat random_float_copy = random_float.duplicate(true);
			assertEquals(random_float.pick(), random_float_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		for (int i = 0; i < 10000; i++)
		{
			RandomFloat random_float = new RandomFloat();
			for (int j = 0; j < 10000; j++)
			{
				random_float.pick();
			}
			RandomFloat random_float_copy = random_float.duplicate(false);
			assertNotEquals(random_float.pick(), random_float_copy.pick());
			random_float.reset();
			random_float_copy.reset();
			assertEquals(random_float.pick(), random_float_copy.pick());
		}
	}
	
	@Test
	public void testReset()
	{
		List<Float> values = new ArrayList<Float>(20);
		RandomFloat rf = new RandomFloat();
		rf.setSeed(0);
		for (int i = 0; i < 20; i++)
		{
			values.add(rf.pick());
		}
		rf.reset();
		for (int i = 0; i < 20; i++)
		{
			assertEquals(values.get(i), rf.pick());
		}
	}
}
