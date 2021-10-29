package ca.uqac.lif.synthia.string;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.random.RandomFloat;
import ca.uqac.lif.synthia.relative.SubStrings;

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

		for (int i = 0; i < 10000; i++)
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

		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
		}


	}

	@Test
	public void duplicateWithoutState()
	{
		List<String> substrings = new SubStrings("abbc").getSubStrings();
		RandomSubString random_substring = new RandomSubString("abbc");

		for (int i = 0; i < 1000; i++)
		{
			random_substring.pick();
		}

		RandomSubString random_substring_copy = (RandomSubString) random_substring
				.duplicate(false);

		int counter = 0;
		for (int i = 0; i < 10000; i++)
		{
			if (random_substring.pick() == random_substring_copy.pick())
			{
				counter ++;
			}

		}
		Assertions.assertTrue(counter < 10000);

		random_substring.reset();
		random_substring_copy.reset();

		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertEquals(random_substring.pick(),random_substring_copy.pick());
		}
	}

	@Test
	public void shrink()
	{
		List<String> substrings = new SubStrings("foobarbaz").getSubStrings();
		RandomSubString random_substring = new RandomSubString("foobarbaz");

		String smaller_string = random_substring.pick();

		while(smaller_string.equals("foobarbaz") || (smaller_string.equals("")))
		{
			smaller_string = random_substring.pick();
		}

		Shrinkable<String> relative_substring = random_substring.shrink(smaller_string, new RandomFloat());
		Assertions.assertTrue(relative_substring.getClass().getSimpleName().equals("RandomSubString"));

		RandomSubString smaller_substring = (RandomSubString) relative_substring;

		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertTrue(((smaller_substring.pick().length() < "foobarbaz".length()) &&
					(smaller_substring.pick().length() <= smaller_string.length())));
		}

	}

	@Test
	public void getNothingPicker()
	{
		RandomSubString random_substring = new RandomSubString("");
		Shrinkable<String> relative_picker = random_substring.shrink(random_substring.pick(), new RandomFloat());

		Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
	}
}
