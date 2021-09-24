package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomSubString;
import ca.uqac.lif.synthia.random.SeedsForRandomGenerationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomSubStringTest
{
	@Test
	public void subString()
	{
		List<String> substrings = new SubStrings("abbc").getSubStrings();
		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomSubString random_substring = new RandomSubString("abbc");
			random_substring.setSeed(int_list.get(i));
			for (int j = 0; j < 100; j++)
			{
				String picked_string = random_substring.pick();
				Assertions.assertEquals(true, substrings.contains(picked_string));
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
			RandomSubString random_substring = new RandomSubString("abbc");
			random_substring.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_substring.pick();
			}

			RandomSubString random_substring_copy = (RandomSubString) random_substring
					.duplicate(true);

			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
			}
		}

	}

	@Test
	public void duplicateWithoutState()
	{

		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomSubString random_substring = new RandomSubString("abbc");
			random_substring.setSeed(int_list.get(i));

			for (int j = 0; j < 100; j++)
			{
				random_substring.pick();
			}

			RandomSubString random_substring_copy = (RandomSubString) random_substring
					.duplicate(false);

			int counter = 0;
			for (int j = 0; j < 100; j++)
			{
				if (random_substring.pick() == random_substring_copy.pick())
				{
					counter ++;
				}

			}
			Assertions.assertTrue(counter < 100);

			random_substring.reset();
			random_substring_copy.reset();

			for (int j = 0; j < 100; j++)
			{
				Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
			}
		}


	}

	@Test
	public void getPicker()
	{

		SeedsForRandomGenerationTests seeds = new SeedsForRandomGenerationTests();
		List<Integer> int_list = seeds.getGeneralSeeds();
		for (int i = 0; i < 10; i++)
		{
			RandomSubString random_substring = new RandomSubString("abbc");
			random_substring.setSeed(int_list.get(i));
			String smaller_string = random_substring.pick();

			while(smaller_string.equals("foobarbaz") || (smaller_string.equals("")))
			{
				smaller_string = random_substring.pick();
			}

			RelativePicker relative_substring = random_substring.getPicker(smaller_string);
			Assertions.assertTrue(relative_substring.getClass().getSimpleName().equals("RandomSubString"));

			RandomSubString smaller_substring = (RandomSubString) relative_substring;

			for (int j = 0; j < 100; j++)
			{
				Assertions.assertTrue(((smaller_substring.pick().length() < "foobarbaz".length()) &&
						(smaller_substring.pick().length() <= smaller_string.length())));
			}
		}



	}

	@Test
	public void getNothingPicker()
	{
		RandomSubString random_substring = new RandomSubString("");
		RelativePicker relative_picker = random_substring.getPicker(random_substring.pick());

		Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
	}

}
