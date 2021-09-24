package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomPrefix;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RandomPrefixTest
{
	@Test
	public void property()
	{
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		String s = "foobarbaz";
		for (int i = 0; i < 10; i++)
		{
			RandomPrefix random_prefix = new RandomPrefix(s);
			random_prefix.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				Assert.assertTrue(random_prefix.pick().length() <= s.length());
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
			RandomPrefix random_prefix = new RandomPrefix(s);
			random_prefix.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_prefix.pick();
			}
			RandomPrefix random_prefix_copy = random_prefix.duplicate(true);

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
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
			RandomPrefix random_prefix = new RandomPrefix(s);
			random_prefix.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				random_prefix.pick();
			}

			RandomPrefix random_prefix_copy = random_prefix.duplicate(false);
			int counter = 0;

			for (int j = 0; j < 100; j++)
			{
				if(random_prefix.pick().equals(random_prefix_copy.pick()))
				{
					counter ++;
				}
			}

			Assert.assertTrue(counter < 100);

			random_prefix.reset();
			random_prefix_copy.reset();

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
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
			RandomPrefix random_prefix = new RandomPrefix(s);
			random_prefix.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				String picked_string = random_prefix.pick();
				RelativePicker relative_picker = random_prefix.getPicker(picked_string);

				if(picked_string.isEmpty())
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
				}
				else
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomPrefix"));
				}

			}

		}

	}
}
