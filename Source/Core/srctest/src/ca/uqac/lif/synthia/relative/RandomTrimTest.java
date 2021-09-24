package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomTrim;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RandomTrimTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomTrim random_trim = new RandomTrim(s);
			random_trim.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				Assert.assertTrue(random_trim.pick().length() <= s.length());
			}
		}

	}

	@Test
	public void duplicateWithState()
	{
		String s = "foobarbaz";
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomTrim random_trim = new RandomTrim(s);
			random_trim.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_trim.pick();
			}
			RandomTrim random_trim_copy = random_trim.duplicate(true);

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_trim.pick(), random_trim_copy.pick());
			}
		}

	}

	@Test
	public void duplicateWithoutState()
	{
		String s = "foobarbaz";
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomTrim random_trim = new RandomTrim(s);
			random_trim.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_trim.pick();
			}

			RandomTrim random_trim_copy = random_trim.duplicate(false);
			int counter = 0;

			for (int j = 0; j < 100; j++)
			{
				if(random_trim.pick().equals(random_trim_copy.pick()))
				{
					counter ++;
				}
			}

			Assert.assertTrue(counter < 100);

			random_trim.reset();
			random_trim_copy.reset();

			for (int j = 0; j < 100; j++)
			{
				Assert.assertEquals(random_trim.pick(), random_trim_copy.pick());
			}
		}

	}

	@Test
	public void getPicker()
	{
		String s = "foobarbaz";
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomTrim random_trim = new RandomTrim(s);
			random_trim.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				String picked_string = random_trim.pick();
				RelativePicker relative_picker = random_trim.getPicker(picked_string);

				if(picked_string.isEmpty())
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
				}
				else
				{
					Assert.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomTrim"));
				}

			}
		}

	}
}
