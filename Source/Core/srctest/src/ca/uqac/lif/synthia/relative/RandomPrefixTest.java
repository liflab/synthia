package ca.uqac.lif.synthia.relative;

import ca.uqac.lif.synthia.random.RandomPrefix;
import org.junit.Assert;
import org.junit.Test;

public class RandomPrefixTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);
		for (int i = 0; i < 10000; i++)
		{
			Assert.assertTrue(random_prefix.pick().length() <= s.length());
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
			Assert.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
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

		Assert.assertTrue(counter < 100000);

		random_prefix.reset();
		random_prefix_copy.reset();

		for (int i = 0; i < 100000; i++)
		{
			Assert.assertEquals(random_prefix.pick(), random_prefix_copy.pick());
		}
	}

	@Test
	public void getPicker()
	{
		String s = "foobarbaz";
		RandomPrefix random_prefix = new RandomPrefix(s);

		for (int i = 0; i < 10000; i++)
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
