package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.replay.Playback;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AffineTransformTest
{
	@ Test
  public void sameValuesSameSeedInteger()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(int_list.get(i));
			int random_seed = random_integer.pick();
			int random_val = random_integer.pick();
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			RandomInteger random_integer2 = new RandomInteger(0, 1000);
			random_integer1.setSeed(random_seed);
			random_integer2.setSeed(random_seed);
			AffineTransform.AffineTransformInteger affine_transform_integer = new AffineTransform
          .AffineTransformInteger(random_integer1, random_val, 0);
			AffineTransform.AffineTransformInteger affine_transform_integer1 = new AffineTransform
          .AffineTransformInteger(random_integer2, random_val, 0);
			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(affine_transform_integer.pick(), affine_transform_integer1.pick());
			}
		}
	}

	@ Test
  public void integerInterval()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int j=0; j<10;j++)
		{
			int min = 0;
			int max = 25;
			RandomInteger random_integer = new RandomInteger(min, max);
			random_integer.setSeed(int_list.get(j));
			AffineTransform.AffineTransformInteger affine_transform_integer = new AffineTransform
					.AffineTransformInteger(random_integer, 2, 0);
			for (int i = 0; i < 100; i++)
			{
				int random_val = affine_transform_integer.pick();
				Assertions.assertTrue(min <= random_val && random_val <= (2 * max));
			}
		}

	}

	@ Test
  public void sameValuesSameSeedFloat()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(random_seed);

			int random_val = random_integer.pick();
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			random_float.setSeed(random_seed);
			random_float1.setSeed(random_seed);
			AffineTransform.AffineTransformFloat affine_transform_float = new AffineTransform
          .AffineTransformFloat(random_float, random_val, 0);
			AffineTransform.AffineTransformFloat affine_transform_float1 = new AffineTransform
          .AffineTransformFloat(random_float1, random_val, 0);
			float f;
			float f1;
			for (int j = 0; j < 100; j++)
			{
				f = affine_transform_float.pick();
				f1 = affine_transform_float1.pick();
				Assertions.assertEquals(f, f1);
			}
		}
	}

    @ Test
    public void floatInterval()
    {
			SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
			List<Integer> int_list = seeds.getGeneralSeeds();
			for(int j=0; j<10; j++)
			{
				int min = 0;
				int max = 25;
				int random_seed = int_list.get(j);
				RandomFloat random_float = new RandomFloat(min, max);
				random_float.setSeed(random_seed);
				AffineTransform.AffineTransformFloat affine_transform_float = new AffineTransform
						.AffineTransformFloat(random_float, 2, 0);
				for (int i = 0; i < 100; i++)
				{
					float random_val = affine_transform_float.pick();
					Assertions.assertTrue(min <= random_val && random_val <= (2 * max));
				}
			}

    }

	@Test
  public void outputTest()
	{
		int m = 2;
		float m1 = (float) 3.57;
		int b = 5;
		float b1 = (float) 10;
		Integer[] integers = new Integer[] { 2, 4, 6, 8 };
		Float[] floats = new Float[] { (float) 7, (float) 14, (float) 21, (float) 28 };
		Integer[] affine_t_int_results = new Integer[] { 9, 13, 17, 21 };
		Float[] affine_t_float_results = new Float[] { (((float) 7 * (float) 3.57) + (float) 10),
				(((float) 14 * (float) 3.57) + (float) 10), (((float) 21 * (float) 3.57) + (float) 10),
				(((float) 28 * (float) 3.57) + (float) 10) };
		Playback playback_int = new Playback(integers);
		Playback playback_float = new Playback(floats);
		AffineTransform affine_transform_int = new AffineTransform.AffineTransformInteger(playback_int,
				m, b);
		AffineTransform affine_transform_float = new AffineTransform.AffineTransformFloat(
				playback_float, m1, b1);
		for (int i = 0; i < affine_t_int_results.length; i++)
		{
			Assertions.assertEquals(affine_t_int_results[i], affine_transform_int.pick());
			Assertions.assertEquals(affine_t_float_results[i], affine_transform_float.pickFloat());
		}
	}

	@Test
	public void duplicateWithStateFloat()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(random_seed);
			int random_val = random_integer.pick();
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(random_seed);
			AffineTransform.AffineTransformFloat affine_transform_float = new AffineTransform
					.AffineTransformFloat(random_float, random_val, 0);
			AffineTransform.AffineTransformFloat affine_transform_float_copy = affine_transform_float
					.duplicate(true);
			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(affine_transform_float.pickFloat(),
						affine_transform_float_copy.pickFloat());
			}
		}
	}

	@Test
	public void duplicateWithoutStateFloat()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(random_seed);
			int random_val = random_integer.pick();
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(random_seed);
			AffineTransform.AffineTransformFloat affine_transform_float = new AffineTransform
					.AffineTransformFloat(random_float, random_val, 0);
			for (int j = 0; j < 100; j++)
			{
				affine_transform_float.pickFloat();
			}
			AffineTransform.AffineTransformFloat affine_transform_float_copy = affine_transform_float
					.duplicate(false);
			Assertions.assertNotEquals(affine_transform_float.pickFloat(),
					affine_transform_float_copy.pickFloat());
			affine_transform_float.reset();
			affine_transform_float_copy.reset();
			Assertions.assertEquals(affine_transform_float.pickFloat(),
					affine_transform_float_copy.pickFloat());
		}
	}

	@Test
	public void duplicateWithStateInteger()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(random_seed);
			int random_val = random_integer.pick();
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			random_integer1.setSeed(random_seed);
			AffineTransform.AffineTransformInteger affine_transform_integer = new AffineTransform
					.AffineTransformInteger(random_integer1, random_val, 0);
			for (int j = 0; j < 100; j++)
			{
				affine_transform_integer.pick();
			}
			AffineTransform.AffineTransformInteger affine_transform_integer_copy =
					affine_transform_integer.duplicate(true);
			Assertions.assertEquals(affine_transform_integer.pick(),
					affine_transform_integer_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutStateInteger()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(0, 1000000);
			random_integer.setSeed(random_seed);
			RandomInteger random_integer1 = new RandomInteger(0, 1000);
			random_integer1.setSeed(random_seed);

			AffineTransform.AffineTransformInteger affine_transform_integer = new AffineTransform
					.AffineTransformInteger(random_integer1, 1, 0);
			for (int j = 0; j < 100; j++)
			{
				affine_transform_integer.pick();
			}
			AffineTransform.AffineTransformInteger affine_transform_integer_copy =
					affine_transform_integer.duplicate(false);

			int int1 = affine_transform_integer.pick();
			int int2 = affine_transform_integer_copy.pick();

			Assertions.assertNotEquals(int1, int2);

			affine_transform_integer.reset();
			affine_transform_integer_copy.reset();
			Assertions.assertEquals(affine_transform_integer.pick(),
					affine_transform_integer_copy.pick());
		}
	}
}
