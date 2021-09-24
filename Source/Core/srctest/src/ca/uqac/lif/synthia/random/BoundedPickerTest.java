package ca.uqac.lif.synthia.random;

import ca.uqac.lif.synthia.exception.NoMoreElementException;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;



public class BoundedPickerTest
{
	@Test public void sameValuesSameSeed()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{

			RandomInteger random_integer1 = new RandomInteger(1, 1000);
			RandomInteger random_integer2 = new RandomInteger(1, 1000);
			RandomFloat random_float = new RandomFloat();
			RandomFloat random_float1 = new RandomFloat();
			int randomSeed = int_list.get(i);
			random_integer1.setSeed(randomSeed);
			random_integer2.setSeed(randomSeed);
			random_float.setSeed(randomSeed);
			random_float1.setSeed(randomSeed);
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer1.pick());
			BoundedPicker bounded_picker1 = new BoundedPicker(random_float1, random_integer2.pick());
			while (!bounded_picker.isDone())
			{
				Assert.assertEquals(bounded_picker.pick(), bounded_picker1.pick());
			}
		}
	}

	@Test
  public void duplicateWithState()
	{
		int min = 50;
		int max = 10000;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(random_seed);
			random_integer.setSeed(random_seed);
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer.pick());

			for (int j = 0; j < (min - 1) ; j++)
			{
				bounded_picker.pick();
			}

			BoundedPicker bounded_picker_copy = bounded_picker.duplicate(true);

			while (!bounded_picker.isDone())
			{
				Assert.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}

		}
	}

	@Test
  public void duplicateWithoutState()
	{
		int min = 50;
		int max = 10000;
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			int random_seed = int_list.get(i);
			RandomInteger random_integer = new RandomInteger(min, max);
			RandomFloat random_float = new RandomFloat();
			random_float.setSeed(random_seed);
			random_integer.setSeed(random_seed);
			BoundedPicker bounded_picker = new BoundedPicker(random_float, random_integer.pick());

			for (int j = 0; j < (min - 1); j++)
			{
				bounded_picker.pick();
			}

			BoundedPicker bounded_picker_copy = bounded_picker.duplicate(false);

			while (bounded_picker.isDone())
			{
				Assert.assertNotEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}


			bounded_picker.reset();
			bounded_picker_copy.reset();

			while (!bounded_picker.isDone())
			{
				Assert.assertEquals(bounded_picker.pick(), bounded_picker_copy.pick());
			}
		}
	}

	@Test
	public void isDone()
	{

		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();

		for(int j=0; j<10; j++)
		{
			RandomInteger rand_int = new RandomInteger(0,100);
			rand_int.setSeed(int_list.get(j));
			BoundedPicker bounded_picker = new BoundedPicker(rand_int, 100);
			for (int i = 0; i < 100; i++)
			{
				Assert.assertEquals(false, bounded_picker.isDone());
				bounded_picker.pick();
			}
			Assert.assertEquals(true, bounded_picker.isDone());
		}




	}

	@Test(expected = NoMoreElementException.class)
	public void noMoreElementException()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for(int j=0; j<10; j++)
		{

			RandomInteger rand_int = new RandomInteger(0, 100);
			rand_int.setSeed(int_list.get(j));

			BoundedPicker bounded_picker = new BoundedPicker(rand_int, 100);

			for (int i = 0; i < 100; i++)
			{
				Assert.assertEquals(false, bounded_picker.isDone());
				bounded_picker.pick();
			}

				bounded_picker.pick();

		}

	}
}