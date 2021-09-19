package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomSubString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RandomSubStringTest
{
	@Test
	public void subString()
	{
		List<String> substrings = new SubStrings("abbc").getSubStrings();
		RandomSubString random_substring = new RandomSubString("abbc");

		for (int i = 0; i < 100; i++)
		{
			String picked_string = random_substring.pick();
			Assertions.assertEquals(true, substrings.contains(picked_string));
		}

	}

	@Test
	public void duplicateWithState()
	{
		List<String> substrings = new SubStrings("abbc").getSubStrings();
		RandomSubString random_substring = new RandomSubString("abbc");

		for (int i = 0; i < 100; i++)
		{
			random_substring.pick();
		}

		RandomSubString random_substring_copy = (RandomSubString) random_substring
				.duplicate(true);

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
		}


	}

	@Test
	public void duplicateWithoutState()
	{
		List<String> substrings = new SubStrings("abbc").getSubStrings();
		RandomSubString random_substring = new RandomSubString("abbc");

		for (int i = 0; i < 100; i++)
		{
			random_substring.pick();
		}

		RandomSubString random_substring_copy = (RandomSubString) random_substring
				.duplicate(false);

		int counter = 0;
		for (int i = 0; i < 100; i++)
		{
			if (random_substring.pick() == random_substring_copy.pick())
			{
				counter ++;
			}

		}
		Assertions.assertTrue(counter < 100);

		random_substring.reset();
		random_substring_copy.reset();

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
		}
	}

	@Test
	public void getPicker()
	{
		List<String> substrings = new SubStrings("foobarbaz").getSubStrings();
		RandomSubString random_substring = new RandomSubString("foobarbaz");

		String smaller_string = random_substring.pick();

		while(smaller_string.equals("foobarbaz") || (smaller_string.equals("")))
		{
			smaller_string = random_substring.pick();
		}

		RelativePicker relative_substring = random_substring.getPicker(smaller_string);
		Assertions.assertTrue(relative_substring.getClass().getSimpleName().equals("RandomSubString"));

		RandomSubString smaller_substring = (RandomSubString) relative_substring;

		for (int i = 0; i < 100; i++)
		{
			Assertions.assertTrue(((smaller_substring.pick().length() < "foobarbaz".length()) &&
					(smaller_substring.pick().length() <= smaller_string.length())));
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
