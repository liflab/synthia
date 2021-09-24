package ca.uqac.lif.synthia.relative;


import ca.uqac.lif.synthia.random.RandomSuffix;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RandomSuffixTest
{
	@Test
	public void property()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		String s = "foobarbaz";
		for (int i = 0; i < 10; i++)
		{
			RandomSuffix random_suffix = new RandomSuffix(s);
			random_suffix.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				Assert.assertTrue(random_suffix.pick().length() <= s.length());
			}
		}

	}

	@Test
	public void duplicateWithState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		String s = "foobarbaz";
		for (int i = 0; i < 10; i++)
		{
			RandomSuffix random_suffix = new RandomSuffix(s);
			random_suffix.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_suffix.pick();
			}
			RandomSuffix random_sufix_copy = random_suffix.duplicate(true);

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_suffix.pick(), random_sufix_copy.pick());
			}
		}

	}

	@Test
	public void duplicateWithoutState()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		String s = "foobarbaz";
		for (int i = 0; i < 10; i++)
		{
			RandomSuffix random_suffix = new RandomSuffix(s);
			random_suffix.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_suffix.pick();
			}

			RandomSuffix random_sufix_copy = random_suffix.duplicate(false);
			int counter = 0;

			for (int j = 0; j < 100; j++)
			{
				if(random_suffix.pick().equals(random_sufix_copy.pick()))
				{
					counter ++;
				}
			}

			Assert.assertTrue(counter < 100);

			random_suffix.reset();
			random_sufix_copy.reset();

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_suffix.pick(), random_sufix_copy.pick());
			}
		}

	}

	@Test
	public void getPicker()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		String s = "foobarbaz";
		for (int i = 0; i < 10; i++)
		{
			RandomSuffix random_suffix = new RandomSuffix(s);
			random_suffix.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				String picked_string = random_suffix.pick();
				RelativePicker relative_picker = random_suffix.getPicker(picked_string);

				if(picked_string.isEmpty())
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
				}
				else
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomSuffix"));
				}

			}
		}

	}
}
