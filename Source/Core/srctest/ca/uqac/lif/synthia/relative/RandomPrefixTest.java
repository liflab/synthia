package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.Shrinkable;
import ca.uqac.lif.synthia.string.RandomPrefix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomPrefixTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);
		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertTrue(random_prefix.pick().length() <= s.length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);
		for (int i = 0; i < 10000; i++)
		{
			random_prefix.pick();
		}
		RandomPrefix random_prefix_copy = random_prefix.duplicate(true);

		for (int i = 0; i < 10000; i++)
		{
			Assertions.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);
		for (int i = 0; i < 10000; i++)
		{
			random_prefix.pick();
		}

		RandomPrefix random_prefix_copy = random_prefix.duplicate(false);
		int counter = 0;

		for (int i = 0; i < 100000; i++)
		{
			if(random_prefix.pick().equals(random_prefix_copy.pick()))
			{
				counter ++;
			}
		}

		Assertions.assertTrue(counter < 100000);

		random_prefix.reset();
		random_prefix_copy.reset();

		for (int i = 0; i < 100000; i++)
		{
			Assertions.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
		}
	}

	@Test
	public void shrink()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);

		for (int i = 0; i < 10000; i++)
		{
			String picked_string = random_prefix.pick();
			Shrinkable<String> relative_picker = random_prefix.shrink(picked_string);

			if(picked_string.isEmpty())
			{
				Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("NothingPicker"));
			}
			else
			{
				Assertions.assertTrue(relative_picker.getClass().getSimpleName().equals("RandomPrefix"));
			}

		}
	}
}
