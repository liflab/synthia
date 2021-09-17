package ca.uqac.lif.synthia.relative;


import ca.uqac.lif.synthia.random.RandomSuffix;
import org.junit.Assert;
import org.junit.Test;

public class RandomSuffixTest
{
	@Test
	public void property()
	{
		String s = "foobarbaz";
		RandomSuffix random_suffix = new RandomSuffix(s);
		for (int i = 0; i < 10000; i++)
		{
			Assert.assertTrue(random_suffix.pick().length() <= s.length());
		}
	}

	@Test
	public void duplicateWithState()
	{
		String s = "foobarbaz";
		RandomSuffix random_suffix = new RandomSuffix(s);
		for (int i = 0; i < 10000; i++)
		{
			random_suffix.pick();
		}
		RandomSuffix random_sufix_copy = random_suffix.duplicate(true);

		for (int i = 0; i < 10000; i++)
		{
			Assert.assertEquals(random_suffix.pick(), random_sufix_copy.pick());
		}
	}

	@Test
	public void duplicateWithoutState()
	{
		String s = "foobarbaz";
		RandomSuffix random_suffix = new RandomSuffix(s);
		for (int i = 0; i < 10000; i++)
		{
			random_suffix.pick();
		}

		RandomSuffix random_sufix_copy = random_suffix.duplicate(false);
		int counter = 0;

		for (int i = 0; i < 100000; i++)
		{
			if(random_suffix.pick().equals(random_sufix_copy.pick()))
			{
				counter ++;
			}
		}

		Assert.assertTrue(counter < 100000);

		random_suffix.reset();
		random_sufix_copy.reset();

		for (int i = 0; i < 100000; i++)
		{
			Assert.assertEquals(random_suffix.pick(), random_sufix_copy.pick());
		}
	}

	@Test
	public void getPicker()
	{
		String s = "foobarbaz";
		RandomSuffix random_suffix = new RandomSuffix(s);

		for (int i = 0; i < 10000; i++)
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
